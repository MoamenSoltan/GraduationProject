import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import Modal from "../components/Modal";
import toast from "react-hot-toast";

const DetailedInstructor = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const [instructor, setInstructor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [deleteModal, setDeleteModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [editedInstructor, setEditedInstructor] = useState(null);

  useEffect(() => {
    const fetchInstructorDetails = async () => {
      try {
        const response = await axiosPrivate.get(`/admin/instructor/${id}`);
        setInstructor(response.data);
        setEditedInstructor(response.data);
      } catch (err) {
        setError("Failed to fetch instructor details.");
      } finally {
        setLoading(false);
      }
    };

    fetchInstructorDetails();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedInstructor((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleUpdate = async () => {
    try {
      await axiosPrivate.put(`/admin/instructor/${id}`, editedInstructor);
      setInstructor(editedInstructor);
      setIsEditing(false);
      toast.success("Instructor updated successfully!");
    } catch (error) {
      toast.error("Failed to update instructor.");
      console.error("Update error:", error);
    }
  };

  const deleteInstructor = async () => {
    try {
      await axiosPrivate.delete(`/admin/instructor/${id}`);
      toast.success("Instructor deleted successfully");
      navigate("/adminDashboard/create-Instructor");
    } catch (error) {
      toast.error("Error deleting instructor");
      console.error("Error occurred while deleting instructor:", error);
    }
  };

  if (loading) return <div className="text-center py-4">Loading instructor details...</div>;
  if (error) return <div className="text-red-500 text-center py-4">{error}</div>;
  if (!instructor) return <div className="text-center py-4">No instructor found.</div>;

  return (
    <div className="max-w-3xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
      {/* Instructor Name */}
      <h2 className="text-3xl font-semibold text-gray-800 mb-6 flex items-center justify-between">
        {isEditing ? (
          <input
            type="text"
            name="firstName"
            value={editedInstructor?.firstName || ""}
            onChange={handleChange}
            className="border-2 border-blue-500 p-3 rounded-md w-full"
          />
        ) : (
          <span>{`${instructor?.firstName || "N/A"} ${instructor?.lastName || "N/A"}`}</span>
        )}
      </h2>

      <div className="flex justify-center mb-6">
        {instructor?.personalImage ? (
          <img
            src={instructor.personalImage}
            alt="Instructor"
            className="w-40 h-40 rounded-full border-4 border-gray-200 shadow-md"
          />
        ) : (
          <div className="text-gray-500">No profile image available</div>
        )}
      </div>

      {/* Instructor Details */}
      <div className="space-y-4">
        {["email", "gender"].map((field) => (
          <div key={field} className="flex items-center">
            <strong className="text-gray-700 w-[15%]">{field.charAt(0).toUpperCase() + field.slice(1)}:</strong>
            {isEditing ? (
              <input
                type="text"
                name={field}
                value={editedInstructor?.[field] || ""}
                onChange={handleChange}
                className="border-2 border-blue-500 p-2 rounded-md w-2/3"
              />
            ) : (
              <p className="text-gray-600">{instructor?.[field] || "N/A"}</p>
            )}
          </div>
        ))}

        {/* Department */}
        <div className="flex  items-center">
          <strong className="text-gray-700 w-[15%]">Department:</strong>
          {isEditing ? (
            <input
              type="text"
              name="department.departmentName"
              value={editedInstructor?.department?.departmentName || ""}
              onChange={(e) =>
                setEditedInstructor((prev) => ({
                  ...prev,
                  department: {
                    ...prev.department,
                    departmentName: e.target.value,
                  },
                }))
              }
              className="border-2 border-blue-500 p-2 rounded-md w-2/3"
            />
          ) : (
            <p className="text-gray-600 ">{instructor?.department?.departmentName || "N/A"}</p>
          )}
        </div>

        {/* Managed Department */}
        {instructor?.managedDepartment && (
          <div className="flex  items-center">
            <strong className="text-gray-700 mr-5">Managed Department:</strong>
            <p className="text-gray-600">
              {instructor.managedDepartment?.departmentName || "N/A"}
            </p>
          </div>
        )}

        {/* Courses */}
        <div>
          <strong className="text-gray-700">Courses:</strong>
          {Array.isArray(instructor?.courses) && instructor.courses.length > 0 ? (
            <ul className="list-disc pl-4 text-gray-600">
              {instructor.courses.map((course) => (
                <li key={course.courseId}>
                  {course.courseName} ({course.courseCode}) - {course.semester.semesterName}
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-600">No courses assigned</p>
          )}
        </div>
      </div>

      {/* Action Buttons */}
      <div className="mt-6 flex gap-6 justify-center">
        {isEditing ? (
          <>
            <button
              onClick={handleUpdate}
              className="bg-green-600 text-white px-6 py-3 rounded-md hover:bg-green-700 transition"
            >
              Confirm Changes
            </button>
            <button
              onClick={() => {
                setIsEditing(false);
                setEditedInstructor(instructor);
              }}
              className="bg-gray-500 text-white px-6 py-3 rounded-md hover:bg-gray-600 transition"
            >
              Discard
            </button>
          </>
        ) : (
          <>
            <button
              onClick={() => setIsEditing(true)}
              className="bg-yellow-500 text-white px-6 py-3 rounded-md hover:bg-blue-700 transition"
            >
              Edit 
            </button>
            <button
              onClick={() => setDeleteModal(true)}
              className="bg-red-600 text-white px-6 py-3 rounded-md hover:bg-red-700 transition"
            >
              Delete
            </button>
          </>
        )}
      </div>

      {/* Delete Modal */}
      <Modal open={deleteModal} onClose={() => setDeleteModal(false)}>
        <div className="text-center p-6">
          <h3 className="text-lg font-semibold text-gray-800 mb-4">Delete Instructor</h3>
          <p className="text-gray-600 mb-6">Are you sure you want to delete this instructor?</p>
          <button
            onClick={deleteInstructor}
            className="bg-red-600 text-white px-6 py-3 rounded-md hover:bg-red-700 transition"
          >
            Delete Instructor
          </button>
        </div>
      </Modal>
    </div>
  );
};

export default DetailedInstructor;
