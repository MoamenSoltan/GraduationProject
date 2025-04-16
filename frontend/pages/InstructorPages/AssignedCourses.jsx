import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";
import { useNavigate } from "react-router";

const AssignedCourses = () => {
  const axiosPrivate = useAxiosPrivate();

  const [courses, setcourses] = useState([]);
  const [loading, setloading] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        if (loading) return;
        setloading(true);
        const response = await axiosPrivate.get("/instructor/course");
        setcourses(response.data);
        console.log("courses fetched :",response.data);
        
      } catch (error) {
        toast.error(`an error occurred fetching courses :  ${error}`);
      } finally {
        setloading(false);
      }
    };

    fetchCourses();
  }, []);
  return (
    courses.length>0 
    ? <div className="md:w-[80%] w-full m-auto mt-10">
    <h2 className="text-2xl font-semibold mb-4 text-gray-800">
      Assigned courses
    </h2>

    <div className="flex w-full flex-row flex-wrap gap-4">
      {courses.map((course) => (
        <div
          onClick={() =>
            navigate(
              `/instructorDashboard/Assigned-Courses/${course.courseId}`
            )
          }
          key={course.courseId}
          className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 transform cursor-pointer"
        >
          <div className="p-4 space-y-2">
            <h3 className="text-xl font-semibold text-gray-800">
              {course.courseName}
            </h3>
            <p className="text-sm text-gray-600">Code: {course.courseCode}</p>
            <p className="text-sm text-gray-600 capitalize">
              Grade: {course.grade.replaceAll("_", " ").toLowerCase()}
            </p>
            <p className="text-sm text-gray-600">
              Semester: {course.semester.semesterName}
            </p>
            <p className="text-sm text-gray-600">
              Year: {course.semester.year}
            </p>
            <div className="flex justify-between items-center mt-2">
              <button className="text-blue-500 hover:underline">
                View Details
              </button>
            </div>
          </div>
        </div>
      ))}
    </div>
  </div>
  :
  <div className="md:w-[80%] w-full m-auto mt-10">
     <h2 className="text-2xl font-semibold mb-4 text-gray-800">
      Assigned courses
    </h2>
    <div className="w-[80%] mx-auto h-[2px] bg-gray-200">

    </div>
    <h3 className="text-xl mx-auto font-semibold mb-4 w-auto mt-10 text-center text-gray-400">no courses assigned yet</h3>
  </div>
  );
};

export default AssignedCourses;
