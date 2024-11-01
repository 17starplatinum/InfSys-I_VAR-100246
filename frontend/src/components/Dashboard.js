export const DashboardComponent = ({ setPage }) => {
  return (
    <div className="container-fluid py-5">
      <div className="row">
        {[
          {
            page: "organizations",
            title: "Organizations",
            icon: "fa fa-building",
          },
          {
            page: "workers",
            title: "Workers",
            icon: "fa fa-person-digging",
          },
          {
            page: "persons",
            title: "Persons",
            icon: "fa fa-people-group",
          },
        //   {
        //     page: "organizations",
        //     title: "Organizations",
        //     icon: "fa fa-building",
        //   },
        ].map((item, index) => (
          <div className="col-lg-4" key={index}>
            <div className="card" onClick={() => setPage(item.page)}>
              <div className="card-header">
                <h2 className="card-title">{item.title}</h2>
              </div>
              <div className="card-body">
                <h1 className="text-center">
                  <i className={item.icon}></i>
                </h1>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
