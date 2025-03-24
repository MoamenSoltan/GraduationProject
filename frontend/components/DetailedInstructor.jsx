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

  if (loading) return <p>Loading instructor details...</p>;
  if (error) return <p className="text-red-500">{error}</p>;
  if (!instructor) return <p>No instructor found.</p>;

  return (
    <div className="max-w-3xl mx-auto p-6 bg-white shadow-md rounded-lg">
      {/* Instructor Name */}
      <h2 className="text-2xl font-bold text-gray-800 mb-4">
        {isEditing ? (
          <input
            type="text"
            name="firstName"
            value={editedInstructor?.firstName || ""}
            onChange={handleChange}
            className="border border-blue-500 p-2 rounded w-full"
          />
        ) : (
          `${instructor?.firstName || "N/A"} ${instructor?.lastName || "N/A"}`
        )}
      </h2>

      {/* Personal Image */}
      {instructor?.personalImage ? (
        <img
          src={instructor.personalImage}
          alt="Instructor"
          className="w-32 h-32 rounded-full mb-4"
        />
      ) : (
        <p className="text-gray-500">No profile image available</p>
      )}
      <strong className="text-gray-600">Instructor ID:</strong>
      <p className="text-gray-600">{instructor.instructorId}</p>

      {/* Instructor Details */}
      <div className="space-y-2">
        {["email", "gender"].map((field) => (
          <div key={field}>
            <strong className="text-gray-600 capitalize">{field}:</strong>
            {isEditing ? (
              <input
                type="text"
                name={field}
                value={editedInstructor?.[field] || ""}
                onChange={handleChange}
                className="border border-blue-500 p-2 rounded w-full"
              />
            ) : (
              <p className="text-gray-600">{instructor?.[field] || "N/A"}</p>
            )}
          </div>
        ))}

        {/* Department */}
        <div>
          <strong className="text-gray-600">Department:</strong>
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
              className="border border-blue-500 p-2 rounded w-full"
            />
          ) : (
            <p className="text-gray-600">
              {instructor?.department?.departmentName || "N/A"}
            </p>
          )}
        </div>

        {/* Managed Department */}
        {instructor?.managedDepartment && (
          <div>
            <strong className="text-gray-600">Managed Department:</strong>
            <p className="text-gray-600">
              {instructor.managedDepartment?.departmentName || "N/A"}
            </p>
          </div>
        )}

        {/* Courses */}
        <div>
          <strong className="text-gray-600">Courses:</strong>
          {Array.isArray(instructor?.courses) &&
          instructor.courses.length > 0 ? (
            <ul className="list-disc pl-4">
              {instructor.courses.map((course) => (
                <li key={course.courseId}>
                  {course.courseName} ({course.courseCode}) -{" "}
                  {course.semester.semesterName}
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-600">No courses assigned</p>
          )}
        </div>
      </div>

      {/* Action Buttons */}
      <div className="mt-6 flex gap-4">
        {isEditing ? (
          <>
            <button
              onClick={handleUpdate}
              className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
            >
              Confirm Changes
            </button>
            <button
              onClick={() => {
                setIsEditing(false);
                setEditedInstructor(instructor);
              }}
              className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Discard
            </button>
          </>
        ) : (
          <>
            <button
              onClick={() => setIsEditing(true)}
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              Update
            </button>
            <button
              onClick={() => setDeleteModal(true)}
              className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
            >
              Delete
            </button>
          </>
        )}
      </div>

      {/* Delete Modal */}
      <Modal open={deleteModal} onClose={() => setDeleteModal(false)}>
        <h3 className="text-lg font-bold mb-4">Delete Instructor</h3>
        <p className="text-gray-600">
          Are you sure you want to delete this instructor?
        </p>
        <button
          onClick={deleteInstructor}
          className="bg-red-600 text-white px-4 py-2 mt-2 rounded hover:bg-red-700"
        >
          Delete Instructor
        </button>
      </Modal>
    </div>
  );
};

export default DetailedInstructor;
