import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";
import Modal from "../../components/Modal";

const CreateCourse = () => {
  const [course, setCourse] = useState({
    courseName: "",
    courseCode: "",
    credit: null,//required but for number field , set to null
    description: "",
    maxStudents: null,
    year: "",
    type: "",
    schedule: "",
    departmentId: 0,
    instructorId: 0,
    prerequisiteCourseId: null,
    yearLevel: 0,
    semesterName: "",
  });

  const [reFetch, setReFetch] = useState(false);
  const [errors, setErrors] = useState({});
  const [allCourses, setAllCourses] = useState([]);
  const [allInstructors, setAllInstructors] = useState([]);
  const [modal, setModal] = useState(false);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const response = await axiosPrivate.get("/admin/course");
        setAllCourses(response.data);
      } catch (error) {
        toast.error("Error fetching courses");
      }
    };

    const fetchInstructors = async () => {
      try {
        const response = await axiosPrivate.get("/admin/instructor");
        setAllInstructors(response.data);
      } catch (error) {
        toast.error("Error fetching instructors");
      }
    };

    fetchCourses();
    fetchInstructors();
  }, [reFetch]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCourse((prev) => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    let newErrors = {};
    if (!course.courseName.trim())
      newErrors.courseName = "Course Name is required";
    if (!course.courseCode.trim())
      newErrors.courseCode = "Course Code is required";
    if (!course.credit) newErrors.credit = "Credit Hours are required";
    if (!course.year) newErrors.year = "Year is required";
    if (!course.type) newErrors.type = "type is required";


    if (!course.description.trim())
      newErrors.description = "Description is required";
    if (!course.maxStudents) newErrors.maxStudents = "Max Students is required";
    if (!course.schedule.trim()) newErrors.schedule = "Schedule is required";
    if (!course.departmentId) newErrors.departmentId = "Department is required";
    if (!course.instructorId) newErrors.instructorId = "Instructor is required";
    if (!course.yearLevel) newErrors.yearLevel = "Year Level is required";
    if (!course.semesterName)
      newErrors.semesterName = "Semester Name is required";
  

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      await axiosPrivate.post("/admin/course", course);
      setReFetch((prev) => !prev);
      toast.success("Course added successfully!");
      setModal(false);
      setCourse({
        courseName: "",
        courseCode: "",
        credit: 0,
        description: "",
        maxStudents: 0,
        year: "FIRST_YEAR",
        type: "",
        schedule: "",
        departmentId: 0,
        instructorId: 0,
        prerequisiteCourseId: null, //optional
        yearLevel: 0,
        semesterName: "spring",
      });
      setErrors({});
    } catch (error) {
      console.log("course details : ",course);
      
      toast.error(
        `Error adding course: ${
          error.response?.data?.detail || "Unknown error"
        }`
      );
    }
  };

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="sub-text-2">Courses</h2>
      <div className="flex w-full flex-row flex-wrap gap-2">
        {allCourses.map((course) => (
          <div
            key={course.id}
            className="rounded-sm shadow-xl p-4 hover:scale-105 transition-all cursor-pointer"
          >
            <h3 className="text-lg">{course.courseName}</h3>
            <p className="text-sm">Code: {course.courseCode}</p>
            <p className="text-sm">Credit Hours: {course.credit}</p>
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
        <form className="w-[100%] p-4" onSubmit={handleSubmit}>
          <h2 className="sub-text">Add Course</h2>
          <input
            type="text"
            name="courseName"
            placeholder="Course Name"
            value={course.courseName}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.courseName && (
            <p className="text-red-500">{errors.courseName}</p>
          )}
          <input
            type="text"
            name="courseCode"
            placeholder="Course Code"
            value={course.courseCode}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.courseCode && (
            <p className="text-red-500">{errors.courseCode}</p>
          )}
          <input
            type="number"
            name="credit"
            placeholder="Credit Hours"
            value={course.credit}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.credit && <p className="text-red-500">{errors.credit}</p>}
          <textarea
            name="description"
            placeholder="Description"
            value={course.description}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.description && (
            <p className="text-red-500">{errors.description}</p>
          )}
          <input
            type="number"
            name="maxStudents"
            placeholder="Max Students"
            value={course.maxStudents}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.maxStudents && (
            <p className="text-red-500">{errors.maxStudents}</p>
          )}
          <input
            type="text"
            name="schedule"
            placeholder="Schedule , example : Tue & Thu, 2:00 PM - 3:30 PM"
            value={course.schedule}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          />
          {errors.schedule && <p className="text-red-500">{errors.schedule}</p>}
          <select
            name="departmentId"
            value={course.departmentId}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Department</option>
            <option value="1">General</option>
            <option value="2">CS</option>
            <option value="3">IT</option>
            <option value="4">IS</option>
          </select>
          {errors.departmentId && (
            <p className="text-red-500">{errors.departmentId}</p>
          )}
          <select
            name="instructorId"
            value={course.instructorId}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="" className="">Select Instructor</option>
            {allInstructors.map((instructor) => (
              <option key={instructor.instructorId} value={instructor.instructorId}>
                {instructor.firstName} {instructor.lastName}
                
              </option>
            ))}
          </select>
          {errors.instructorId && (
            <p className="text-red-500">{errors.instructorId}</p>
          )}
          <select
            name="year"
            value={course.year}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Academic Year</option>
            <option value="FIRST_YEAR">First Year</option>
            <option value="SECOND_YEAR">Second Year</option>
            <option value="THIRD_YEAR">Third Year</option>
            <option value="FOURTH_YEAR">Fourth Year</option>
          </select>
          {errors.year && (
            <p className="text-red-500">{errors.year}</p>
          )}
          <select
            name="type"
            value={course.type}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Type</option>

            <option value="REQUIRED">Required</option>
            <option value="OPTIONAL">Optional</option>
            <option value="RECOMMENDED">Recommended</option>
          </select>
          {errors.type && (
            <p className="text-red-500">{errors.type}</p>
          )}

          <select
            name="yearLevel"
            value={course.yearLevel}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Year Level</option>
            {[2025, 2026, 2027, 2028, 2029, 2030].map((year) => (
              <option key={year} value={year}>
                {year}
              </option>
            ))}
          </select>
          {errors.yearLevel && (
            <p className="text-red-500">{errors.yearLevel}</p>
          )}

          <select
            name="semesterName"
            value={course.semesterName}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            <option value="">Select Semester Name</option>
            <option value="Spring">Spring</option>
            <option value="Summer">Summer</option>
            <option value="Fall">Fall</option>
          </select>
          {errors.semesterName && (
            <p className="text-red-500">{errors.semesterName}</p>
          )}

          <select
            name="prerequisiteCourseId"
            value={course.prerequisiteCourseId}
            onChange={handleChange}
            className="w-full p-2 border rounded mb-2"
          >
            
            <option value="">none</option>

            {allCourses.map((course) => (
              <option key={course.courseId} value={course.courseId}>
                {course.courseName}
              </option>
            ))}
          </select>
          {/* {errors.prerequisiteCourseId && (
            <p className="text-red-500">{errors.prerequisiteCourseId}</p>
          )} */}

          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded mt-4 hover:bg-blue-600"
          >
            Submit
          </button>
        </form>
      </Modal>
    </div>
  );
};

export default CreateCourse;
