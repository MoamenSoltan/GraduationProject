import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";

const DetailedInstructorCourses = () => {
  const [students, setStudents] = useState([]);
  const [loading, setloading] = useState(false);
  const { id } = useParams();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [CSV, setCSV] = useState(null);
  const [reFetch, setreFetch] = useState(false)

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        if (loading) return;
        setloading(true);
        const response = await axiosPrivate.get(
          `/instructor/course/${id}/student`
        );
        setStudents(response.data);
        console.log("students fetched:", response.data);
      } catch (error) {
        toast.error(`An error occurred: ${error}`);
      } finally {
        setloading(false);
      }
    };

    fetchStudents();
  }, [id,reFetch]);

  const handleUploadCSV = async (e) => {
    e.preventDefault();

    
    try {
        const file = e.target.files[0]
      
      const formData = new FormData();
      formData.append("file", file);
      const response = await axiosPrivate.put(`/instructor/course/${id}/student/degree/upload`,formData,{headers:{
        'Content-Type': 'multipart/form-data',
      }})
      setreFetch(prev=>!prev)
    } catch (error) {
        toast.error(`An error occurred: ${error}`);
    }
    
  };





  const handleDownloadCSV = async () => {
    try {
      const res = await axiosPrivate.get(
        `/instructor/course/${id}/student/result/download`,
        {
          responseType: "blob", // very important!
        }
      );

      const url = URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "students.csv"); // or any filename
      document.body.appendChild(link);
      link.click();
      link.remove();
      toast.success("Download successful!");
    } catch (error) {
      toast.error(`Error occurred: ${error.message}`);
    }
  };

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      {loading ? (
        <p className="text-gray-500">Loading...</p>
      ) : students.length === 0 ? (
        <div className="flex justify-center">
          <h3 className="text-xl font-semibold mb-4 text-center text-gray-400">
            No students assigned yet
          </h3>
        </div>
      ) : (
        <div className="overflow-x-auto shadow-md rounded-lg">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4 gap-4">
            <h2 className="text-2xl font-semibold text-gray-800">
              Course Students
            </h2>

            <div className="flex flex-wrap gap-3">
              <button
                onClick={handleDownloadCSV}
                className="px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded-lg hover:bg-blue-700 transition"
              >
                Download CSV
              </button>
              <label className="px-4 py-2 bg-green-600 text-white text-sm font-medium rounded-lg hover:bg-green-700 cursor-pointer transition">
                Upload CSV
                <input
                  type="file"
                  accept=".csv"
                  onChange={(e) => handleUploadCSV(e)}
                  className="hidden"
                />
              </label>
            </div>
          </div>

          <table className="min-w-full text-sm text-left text-gray-600 border border-gray-200">
            <thead className="text-xs text-gray-700 uppercase bg-gray-100">
              <tr>
                <th scope="col" className="px-6 py-3">
                  Student ID
                </th>
                <th scope="col" className="px-6 py-3">
                  Username
                </th>
                <th scope="col" className="px-6 py-3">
                  Email
                </th>
                <th scope="col" className="px-6 py-3">
                  Degree
                </th>
              </tr>
            </thead>
            <tbody>
              {students.map((student) => (
                <tr onClick={()=>navigate(`/instructorDashboard/assigned-Courses/${id}/students/${student.studentId}`,{state:{student}})}
                  key={student.studentId}
                  className="bg-white border-b hover:bg-gray-50"
                >
                  <td className="px-6 py-4">{student.studentId}</td>
                  <td className="px-6 py-4">{student.username}</td>
                  <td className="px-6 py-4">{student.email}</td>
                  <td className="px-6 py-4">{student.degree ?? "N/A"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default DetailedInstructorCourses;
