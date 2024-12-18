package ru.ifmo.se.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.dto.data.OrganizationDTO;
import ru.ifmo.se.dto.data.filter.OrganizationFilterCriteria;
import ru.ifmo.se.entity.data.Address;
import ru.ifmo.se.entity.data.Organization;
import ru.ifmo.se.entity.data.audit.AuditOperation;
import ru.ifmo.se.repository.data.OrganizationRepository;
import ru.ifmo.se.service.data.audit.AuditService;
import ru.ifmo.se.service.user.UserService;
import ru.ifmo.se.util.EntityMapper;
import ru.ifmo.se.util.filter.FilterProcessor;
import ru.ifmo.se.util.pagination.PaginationHandler;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final AuditService auditService;
    private final UserService userService;
    private final EntityMapper entityMapper;
    private final FilterProcessor<OrganizationDTO, OrganizationFilterCriteria> organizationFilterProcessor;
    private final PaginationHandler paginationHandler;
    private final AddressService addressService;
    private static final String NOT_FOUND_MESSAGE = "Organization not found.";

    @Transactional(readOnly = true)
    public Page<OrganizationDTO> getAllOrganizations(String fullName, int page, int size, String sortBy, String sortDirection) {
        OrganizationFilterCriteria organizationFilterCriteria = new OrganizationFilterCriteria();
        organizationFilterCriteria.setFullName(fullName);

        Pageable pageable = paginationHandler.createPageable(page, size, sortBy, sortDirection);
        return organizationFilterProcessor.filter(organizationFilterCriteria, pageable);
    }

    @Transactional(readOnly = true)
    public OrganizationDTO getOrganizationById(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        return entityMapper.toOrganizationDTO(organization);
    }

    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        Address officialAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getOfficialAddress());
        Address postalAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getPostalAddress());
        Organization organization = entityMapper.toOrganizationEntity(organizationDTO, officialAddress, postalAddress);
        organization.setCreatedBy(userService.getCurrentUser());
        Organization savedOrganization = organizationRepository.save(organization);
        auditService.auditOrganization(savedOrganization, AuditOperation.CREATE);
        return entityMapper.toOrganizationDTO(savedOrganization);
    }

    @Transactional
    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDTO) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        if (userService.cantModifyEntity(organization)) {
            throw new IllegalArgumentException("You are not allowed to modify this Organization.");
        }
        Address officialAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getOfficialAddress());
        Address postalAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getPostalAddress());
        organization.setOfficialAddress(officialAddress);
        organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        organization.setEmployeesCount(organizationDTO.getEmployeesCount());
        organization.setFullName(organizationDTO.getFullName());
        organization.setOrganizationType(organizationDTO.getOrganizationType());
        organization.setPostalAddress(postalAddress);

        Organization savedOrganization = organizationRepository.save(organization);
        auditService.auditOrganization(savedOrganization, AuditOperation.UPDATE);
        return entityMapper.toOrganizationDTO(savedOrganization);
    }

    @Transactional
    public Organization createOrUpdateOrganizationForWorker(OrganizationDTO organizationDTO) {
        if(organizationDTO == null) {
            return null;
        }
        Address officialAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getOfficialAddress());
        Address postalAddress = addressService.createOrUpdateAddressForOrganization(organizationDTO.getPostalAddress());
        if (organizationDTO.getId() != null) {
            Organization organization = organizationRepository.findById(organizationDTO.getId()).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
            if (userService.cantModifyEntity(organization)) {
                throw new IllegalArgumentException("You are not allowed to modify this Organization.");
            }
            organization.setOfficialAddress(officialAddress);
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
            organization.setEmployeesCount(organizationDTO.getEmployeesCount());
            organization.setFullName(organizationDTO.getFullName());
            organization.setOrganizationType(organizationDTO.getOrganizationType());
            organization.setPostalAddress(postalAddress);

            Organization savedOrganization = organizationRepository.save(organization);
            auditService.auditOrganization(savedOrganization, AuditOperation.UPDATE);
            return savedOrganization;
        } else {
            Organization organization = entityMapper.toOrganizationEntity(organizationDTO, officialAddress, postalAddress);
            organization.setCreatedBy(userService.getCurrentUser());
            Organization savedOrganization = organizationRepository.save(organization);
            auditService.auditOrganization(savedOrganization, AuditOperation.CREATE);
            return savedOrganization;
        }
    }

    @Transactional
    public void deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        if (userService.cantModifyEntity(organization)) {
            throw new IllegalArgumentException("You are not allowed to delete this Organization.");
        }
        auditService.deleteOrganizationAudits(id);
        organizationRepository.delete(organization);
    }
}
