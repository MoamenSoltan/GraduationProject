import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";
import Modal from "../../components/Modal";

const CreateInstructor = () => {
  const [instructor, setInstructor] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    gender: "",
    departmentId: 1,
    managedDepartmentId: null,
  });

  const [errors, setErrors] = useState({});
  const [allInstructors, setAllInstructors] = useState([]);
  const [modal, setModal] = useState(false);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    const fetchInstructors = async () => {
      try {
        const response = await axiosPrivate.get("/admin/instructor");
        setAllInstructors(response.data);
        console.log("Admin instructors data:", response.data);
      } catch (error) {
        toast.error("Error fetching instructors");
      }
    };
    fetchInstructors();
  }, []);

  // Handle input change for text fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setInstructor((prev) => ({ ...prev, [name]: value }));
  };

  // Handle department selection
  const handleDepartmentChange = (e) => {
    const selectedValue = Number(e.target.value);
    setInstructor((prev) => ({
      ...prev,
      departmentId: selectedValue,
      managedDepartmentId: prev.managedDepartmentId !== null ? selectedValue : null, // Sync if checkbox is checked
    }));
  };

  // Handle checkbox for managed department
  const handleCheckboxChange = (e) => {
    setInstructor((prev) => ({
      ...prev,
      managedDepartmentId: e.target.checked ? prev.departmentId : null,
    }));
  };

  // Validate inputs
  const validate = () => {
    let newErrors = {};

    if (!instructor.firstName.trim()) newErrors.firstName = "First Name is required";
    if (!instructor.lastName.trim()) newErrors.lastName = "Last Name is required";
    if (!instructor.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/.test(instructor.email)) {
      newErrors.email = "Invalid email format";
    }

    if (!instructor.password.trim()) {
      newErrors.password = "Password is required";
    } else if (instructor.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters";
    }

    if (!instructor.gender) newErrors.gender = "Gender is required";
    if (!instructor.departmentId) newErrors.departmentId = "Department is required";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return; // Prevent submission if validation fails
  
    try {
      const response = await axiosPrivate.post("/admin/instructor", instructor);
      const newInstructor = response.data; // Use API response (including ID and any other data)
  
      toast.success("Instructor added successfully!");
      setModal(false);
      
      // Update the state with the new instructor from API
      setAllInstructors((prev) => [...prev, newInstructor]); 
      
      // Reset the form
      setInstructor({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        gender: "",
        departmentId: 1,
        managedDepartmentId: null,
      });
  
      setErrors({});
    } catch (error) {
      toast.error("Error adding instructor");
    }
  };
  

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="sub-text-2">Instructors</h2>

      <div className="flex w-full flex-row flex-wrap gap-2">
        {allInstructors.map((instructor) => (
          <div
            key={instructor.instructorId}
            className="rounded-sm shadow-xl p-4 hover:scale-105 transition-all cursor-pointer"
          >
            <h3 className="text-lg">
              {instructor.firstName} {instructor.lastName}
            </h3>
            <p className="text-sm">{instructor.email}</p>
            <p className="text-sm">{instructor.gender}</p>
            {!instructor.managedDepartment ? (
              <p className="text-sm">{instructor.department.departmentName}</p>
            ) : (
              <p className="text-sm">
                Managed Department: {instructor.managedDepartment.departmentName}
              </p>
            )}
          </div>
        ))}
      </div>

      <button
        onClick={() => setModal(true)}
        className="fixed bottom-5 right-5 p-4 w-28 h-28 rounded-full bg-slate-200 hover:scale-105 transition-all text-2xl"
      >
        +
      </button>

      <Modal open={modal} onClose={() => setModal(false)}>
        <form className="w-full p-4" onSubmit={handleSubmit}>
          <h2 className="sub-text">Add Instructor</h2>

          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={instructor.firstName}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.firstName && <p className="text-red-500">{errors.firstName}</p>}

          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={instructor.lastName}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.lastName && <p className="text-red-500">{errors.lastName}</p>}

          <input
            type="email"
            name="email"
            placeholder="Email"
            value={instructor.email}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.email && <p className="text-red-500">{errors.email}</p>}

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={instructor.password}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.password && <p className="text-red-500">{errors.password}</p>}

          <select
            name="gender"
            value={instructor.gender}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Gender</option>
            <option value="MALE">Male</option>
            <option value="FEMALE">Female</option>
          </select>
          {errors.gender && <p className="text-red-500">{errors.gender}</p>}

          <select
            name="departmentId"
            value={instructor.departmentId}
            onChange={handleDepartmentChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value={1}>General</option>
            <option value={2}>CS</option>
            <option value={3}>IT</option>
            <option value={4}>IS</option>
          </select>
          {errors.departmentId && <p className="text-red-500">{errors.departmentId}</p>}

          <label className="flex items-center gap-2">
            <input type="checkbox" checked={instructor.managedDepartmentId !== null} onChange={handleCheckboxChange} />
            Manage this department
          </label>

          <button type="submit" className="w-full bg-blue-500 text-white p-2 rounded mt-4 hover:bg-blue-600">
            Submit
          </button>
        </form>
      </Modal>
    </div>
  );
};

export default CreateInstructor;
