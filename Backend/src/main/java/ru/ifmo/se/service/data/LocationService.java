package ru.ifmo.se.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.dto.data.LocationDTO;
import ru.ifmo.se.entity.data.Location;
import ru.ifmo.se.entity.data.audit.AuditOperation;
import ru.ifmo.se.exception.EntityDeletionException;
import ru.ifmo.se.repository.data.LocationRepository;
import ru.ifmo.se.service.data.audit.AuditService;
import ru.ifmo.se.service.user.UserService;
import ru.ifmo.se.util.EntityMapper;
import ru.ifmo.se.util.pagination.PaginationHandler;

@Service
@RequiredArgsConstructor
public class LocationService {
    private LocationRepository locationRepository;
    private EntityMapper entityMapper;
    private AuditService auditService;
    private UserService userService;
    private PaginationHandler paginationHandler;
    private static final String NOT_FOUND_MESSAGE = "Location not found";

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Autowired
    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    @Autowired
    public void setPaginationHandler(PaginationHandler paginationHandler) {
        this.paginationHandler = paginationHandler;
    }

    @Transactional(readOnly = true)
    public Page<LocationDTO> getAllLocations(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = paginationHandler.createPageable(page, size, sortBy, sortDirection);
        return locationRepository.findAll(pageable).map(entityMapper::toLocationDTO);
    }

    @Transactional(readOnly = true)
    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        return entityMapper.toLocationDTO(location);
    }

    @Transactional
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = entityMapper.toLocationEntity(locationDTO);
        location.setCreatedBy(userService.getCurrentUser());
        Location savedLocation = locationRepository.save(location);
        auditService.auditLocation(savedLocation, AuditOperation.CREATE);
        return entityMapper.toLocationDTO(savedLocation);
    }

    @Transactional
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));

        location.setX(locationDTO.getX());
        location.setY(locationDTO.getY());
        location.setZ(locationDTO.getZ());

        Location savedLocation = locationRepository.save(location);
        auditService.auditLocation(savedLocation, AuditOperation.UPDATE);
        return entityMapper.toLocationDTO(savedLocation);
    }

    @Transactional
    public Location createOrUpdateLocationForObjects(LocationDTO locationDTO) {
        if(locationDTO == null) {
            Location location = locationRepository.save(null);
            auditService.auditLocation(location, AuditOperation.CREATE);
            return location;
        }
        if (locationDTO.getId() != null) {
            Location location = locationRepository.findById(locationDTO.getId()).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));

            location.setX(locationDTO.getX());
            location.setY(locationDTO.getY());
            location.setZ(locationDTO.getZ());
            Location savedLocation = locationRepository.save(location);
            auditService.auditLocation(savedLocation, AuditOperation.UPDATE);
            return savedLocation;
        } else {
            Location location = entityMapper.toLocationEntity(locationDTO);
            location.setCreatedBy(userService.getCurrentUser());
            Location savedLocation = locationRepository.save(location);
            auditService.auditLocation(savedLocation, AuditOperation.CREATE);
            return savedLocation;
        }
    }

    @Transactional
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        if (location.getPerson() != null) {
            throw new EntityDeletionException("Cannot delete this Location since it is linked to a Person.");
        }
        auditService.deleteLocationAudits(location.getId());
        locationRepository.delete(location);
    }
}
