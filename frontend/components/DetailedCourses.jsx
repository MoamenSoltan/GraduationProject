import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import Modal from "../components/Modal";
import toast from "react-hot-toast";

const DetailedCourses = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate();

    const [course, setCourse] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [deleteModal, setDeleteModal] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editedCourse, setEditedCourse] = useState(null);

    useEffect(() => {
        const fetchCourseDetails = async () => {
            try {
                const response = await axiosPrivate.get(`/admin/course/${id}`);
                setCourse(response.data);
                setEditedCourse({
                    ...response.data,
                    departmentId: response?.data?.department?.departmentId,
                    semesterName: response?.data?.semester?.semesterName,
                    yearLevel: response?.data?.semester?.yearLevel,
                });
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchCourseDetails();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedCourse((prev) => ({ ...prev, [name]: value }));
    };

    const handleUpdate = async () => {
        try {
            await axiosPrivate.put(`/admin/course/${id}`, editedCourse);
            setCourse(editedCourse);
            setIsEditing(false);
            toast.success("Course updated successfully!");
            navigate("/adminDashboard/create-Course");
        } catch (error) {
            toast.error(`Failed to update course: ${error?.response?.data?.detail}`);
        }
    };

    const deleteCourse = async () => {
        try {
            await axiosPrivate.delete(`/admin/course/${id}`);
            toast.success("Course deleted successfully");
            navigate("/adminDashboard/create-Course");
        } catch (error) {
            toast.error("Error deleting course");
        }
    };

    if (loading) return <p>Loading course details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!course) return <p>No course found</p>;

    return (
        <div className="max-w-4xl mt-10 mx-auto p-8 bg-white shadow-xl rounded-xl border border-gray-200">
            <div className="text-center mb-8">
                <h2 className="text-3xl font-semibold text-gray-800">
                    {isEditing ? (
                        <input
                            type="text"
                            name="courseName"
                            value={editedCourse.courseName}
                            onChange={handleChange}
                            className="border-b-2 border-gray-300 text-2xl font-medium p-2 w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    ) : (
                        course.courseName
                    )}
                </h2>
            </div>

            <div className="space-y-6">
                {["courseCode", "credit", "description", "maxStudents", "studentEnrolled", "year", "type", "schedule"].map((field) => (
                    <div key={field} className="flex justify-between items-center text-lg text-gray-700">
                        <span className="font-medium capitalize text-left w-1/3">{field.replace(/([A-Z])/g, " $1")}:</span>
                        {isEditing ? (
                            <input
                                type="text"
                                name={field}
                                value={editedCourse[field]}
                                onChange={handleChange}
                                className="border-b-2 border-gray-300 p-2 w-2/3 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        ) : (
                            <p className="text-gray-500 text-left w-2/3">{course[field]}</p>
                        )}
                    </div>
                ))}
            </div>

            <div className="mt-8">
                <h3 className="text-xl font-semibold text-gray-800 mb-4 text-left">Department</h3>
                <p className="text-gray-600 text-left">{course.department.departmentName}</p>
            </div>

            <div className="mt-8">
                <h3 className="text-xl font-semibold text-gray-800 mb-4 text-left">Instructor</h3>
                <p className="text-gray-600 text-left">{course.instructor.firstName} {course.instructor.lastName}</p>
                <p className="text-gray-600 text-left">{course.instructor.email}</p>
            </div>

            {course.prerequisiteCourse && (
                <div className="mt-8">
                    <h3 className="text-xl font-semibold text-gray-800 mb-4 text-left">Prerequisite Course</h3>
                    <p className="text-gray-600 text-left">{course.prerequisiteCourse.courseName}</p>
                </div>
            )}

            <div className="mt-8">
                <h3 className="text-xl font-semibold text-gray-800 mb-4 text-left">Semester</h3>
                <p className="text-gray-600 text-left">Year Level: {course.semester.yearLevel}</p>
                <p className="text-gray-600 text-left">Semester: {course.semester.semesterName}</p>
            </div>

            <div className="mt-8 flex gap-6 justify-center">
                {isEditing ? (
                    <>
                        <button
                            onClick={handleUpdate}
                            className="bg-blue-600 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Save Changes
                        </button>
                        <button
                            onClick={() => {
                                setIsEditing(false);
                                setEditedCourse(course);
                            }}
                            className="bg-gray-500 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-gray-500"
                        >
                            Cancel
                        </button>
                    </>
                ) : (
                    <>
                        <button
                            onClick={() => setIsEditing(true)}
                            className="bg-yellow-500 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-yellow-500"
                        >
                            Edit Course
                        </button>
                        <button
                            onClick={() => setDeleteModal(true)}
                            className="bg-red-600 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-red-500"
                        >
                            Delete Course
                        </button>
                    </>
                )}
            </div>

            <Modal open={deleteModal} onClose={() => setDeleteModal(false)}>
                <h3 className="text-xl font-semibold text-gray-800 mb-4 text-left">Are you sure you want to delete this course?</h3>
                <div className="flex justify-center gap-6">
                    <button
                        onClick={deleteCourse}
                        className="bg-red-600 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        Yes, Delete
                    </button>
                    <button
                        onClick={() => setDeleteModal(false)}
                        className="bg-gray-500 text-white px-6 py-3 rounded-lg transition-transform transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-gray-500"
                    >
                        Cancel
                    </button>
                </div>
            </Modal>
        </div>
    );
};

export default DetailedCourses;
