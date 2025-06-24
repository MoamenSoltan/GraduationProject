import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";
import Modal from "../../components/Modal";
import { useNavigate } from "react-router";

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

  const [reFetch, setreFetch] = useState(false);
  const [errors, setErrors] = useState({});
  const [allInstructors, setAllInstructors] = useState([]);
  const [modal, setModal] = useState(false);
  const axiosPrivate = useAxiosPrivate();
  const [currentPage, setCurrentPage] = useState(1);
  const cardsPerPage = 12; // You can adjust this number as needed

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
  }, [reFetch]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInstructor((prev) => ({ ...prev, [name]: value }));
  };

  const handleDepartmentChange = (e) => {
    const selectedValue = Number(e.target.value);
    setInstructor((prev) => ({
      ...prev,
      departmentId: selectedValue,
      managedDepartmentId: prev.managedDepartmentId !== null ? selectedValue : null,
    }));
  };

  const handleCheckboxChange = (e) => {
    setInstructor((prev) => ({
      ...prev,
      managedDepartmentId: e.target.checked ? prev.departmentId : null,
    }));
  };

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

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      const response = await axiosPrivate.post("/admin/instructor", instructor);
      const newInstructor = response.data;
      setreFetch(prev => !prev);

      toast.success("Instructor added successfully!");
      setModal(false);

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
      toast.error(`Error adding instructor : ${error.response.data.detail}`);
    }
  };

  const indexOfLastRequest = currentPage * cardsPerPage;
  const indexOfFirstRequest = indexOfLastRequest - cardsPerPage;
  const currentInstructors = allInstructors.slice(indexOfFirstRequest, indexOfLastRequest);
  console.log("Current Requests:", currentInstructors); // Debugging line

  // Handle page change
  const nextPage = () => {
      if (currentPage < Math.ceil(allInstructors.length / cardsPerPage)) {
          setCurrentPage(prevPage => prevPage + 1);
      }
  };

  const prevPage = () => {
      if (currentPage > 1) {
          setCurrentPage(prevPage => prevPage - 1);
      }
  };

  const navigate = useNavigate();

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Instructors</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {currentInstructors.map((instructor) => (
          <div
            key={instructor.instructorId}
            onClick={() => navigate(`/adminDashboard/create-Instructor/${instructor.instructorId}`)}
            className="rounded-lg overflow-hidden shadow-lg hover:scale-105 transition-all cursor-pointer bg-white p-4 border border-gray-200"
          >
            <h3 className="text-lg font-semibold text-gray-700">
              {instructor.firstName} {instructor.lastName}
            </h3>
            <p className="text-sm text-gray-600">{instructor.email}</p>
            <p className="text-sm text-gray-500">{instructor.gender}</p>
            <p className="text-sm text-gray-600">{instructor.department.departmentName}</p>
            {instructor.managedDepartment && (
              <p className="text-sm text-blue-600">
                Managed Department: {instructor.managedDepartment.departmentName}
              </p>
            )}
          </div>
        ))}
      </div>

       {/* Pagination Controls */}
       <div className="flex justify-center items-center space-x-4 mt-6">
                <button 
                    onClick={prevPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === 1}
                >
                    Previous
                </button>
                <span className="text-lg font-semibold">
                    Page {currentPage} of {Math.ceil(allInstructors.length / cardsPerPage)}
                </span>
                <button 
                    onClick={nextPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === Math.ceil(allInstructors.length / cardsPerPage)}
                >
                    Next
                </button>
            </div>

            <button
        onClick={() => setModal(true)}
        className="block mx-auto mt-8 p-4 w-20 h-20 rounded-full bg-blue-600 text-white text-3xl shadow-lg hover:scale-110 transition-all"
        title="Add Material"
      >
        +
      </button>

      <Modal open={modal} onClose={() => setModal(false)}>
        <form className="w-full p-6 bg-white rounded-lg shadow-lg" onSubmit={handleSubmit}>
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Add Instructor</h2>

          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={instructor.firstName}
            onChange={handleChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          />
          {errors.firstName && <p className="text-red-500 text-sm">{errors.firstName}</p>}

          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={instructor.lastName}
            onChange={handleChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          />
          {errors.lastName && <p className="text-red-500 text-sm">{errors.lastName}</p>}

          <input
            type="email"
            name="email"
            placeholder="Email"
            value={instructor.email}
            onChange={handleChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          />
          {errors.email && <p className="text-red-500 text-sm">{errors.email}</p>}

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={instructor.password}
            onChange={handleChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          />
          {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}

          <select
            name="gender"
            value={instructor.gender}
            onChange={handleChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Select Gender</option>
            <option value="MALE">Male</option>
            <option value="FEMALE">Female</option>
          </select>
          {errors.gender && <p className="text-red-500 text-sm">{errors.gender}</p>}

          <select
            name="departmentId"
            value={instructor.departmentId}
            onChange={handleDepartmentChange}
            className="w-full p-3 border border-gray-300 rounded-lg mb-3 focus:ring-2 focus:ring-blue-500"
          >
            <option value={1}>General</option>
            <option value={2}>CS</option>
            <option value={3}>IT</option>
            <option value={4}>IS</option>
          </select>
          {errors.departmentId && <p className="text-red-500 text-sm">{errors.departmentId}</p>}

          <label className="flex items-center gap-2 mb-4">
            <input type="checkbox" checked={instructor.managedDepartmentId !== null} onChange={handleCheckboxChange} />
            Manage this department
          </label>

          <button type="submit" className="w-full bg-blue-500 text-white p-3 rounded-lg mt-4 hover:bg-blue-600 focus:ring-2 focus:ring-blue-500">
            Submit
          </button>
        </form>
      </Modal>
    </div>
  );
};

export default CreateInstructor;
