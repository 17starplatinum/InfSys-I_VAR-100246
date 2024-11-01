import { useState } from "react";

export const LoginPage = ({setPage, setUser}) => {
  const [formData, setFormData] = useState({ username: "", password: "" });

  const updateForm = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const submitForm = async (e) => {
    e.preventDefault();
    console.log(formData)
    setUser({...formData, role: "admin"})
    return false;
  };

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-6 offset-md-3 py-5">
          <form onSubmit={submitForm}>
            <div className="mb-4">
              <h2>Login</h2>
            </div>
            <div className="mb-4">
              <label htmlFor="username">Username</label>
              <input className="form-control" name="username" type="text" onChange={updateForm} />
            </div>
            <div className="mb-4">
              <label htmlFor="password">Password</label>
              <input className="form-control" name="password" type="password" onChange={updateForm} />
            </div>
            <div className="mb-4">
              <button className="btn btn-primary" type="submit">
                <i className="fa fa-sign-in"></i>&nbsp;Login
              </button>
            </div>
          </form>
            <div className="mb-4">
              <button className="btn btn-link" onClick={() => setPage("register")}>
                <i className="fa fa-user"></i>&nbsp;Register
              </button>
            </div>
        </div>
      </div>
    </div>
  );
};
