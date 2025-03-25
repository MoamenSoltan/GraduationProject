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
                // setCourse(response.data) //this is the correct approach , we did the other one for backend preference
                setEditedCourse({...response.data
                    ,departmentId:response?.data?.department?.departmentId,
                    semesterName:response?.data?.semester?.semesterName,
                    yearLevel:response?.data?.semester?.yearLevel}); // Initialize editable fields
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
            navigate("/adminDashboard/create-Course")
        } catch (error) {
            toast.error(`Failed to update course : ${error?.response?.data?.detail}`);
            console.error("Update error:", error);
            console.log("Updated course : ",editedCourse);
            console.log(" course : ",course);

            
        }
    };

    const deleteCourse = async () => {
        try {
            await axiosPrivate.delete(`/admin/course/${id}`);
            toast.success("Course deleted successfully");
            navigate("/adminDashboard/create-Course");
        } catch (error) {
            toast.error("Error deleting course");
            console.error("Error occurred while deleting course:", error);
        }
    };

    if (loading) return <p>Loading course details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!course) return <p>No course found</p>;

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white shadow-md rounded-lg">
            <h2 className="text-2xl font-bold text-gray-800 mb-4">
                {isEditing ? (
                    <input
                        type="text"
                        name="courseName"
                        value={editedCourse.courseName}
                        onChange={handleChange}
                        className="border border-blue-500 p-2 rounded w-full"
                    />
                ) : (
                    course.courseName
                )}
            </h2>

            <div className="space-y-2">
                {["courseCode", "credit", "description", "maxStudents", "studentEnrolled", "year", "type", "schedule"].map((field) => (
                    <div key={field}>
                        <strong className="text-gray-600 capitalize">{field.replace(/([A-Z])/g, " $1")}:</strong>
                        {isEditing ? (
                            <input
                                type="text"
                                name={field}
                                value={editedCourse[field]}
                                onChange={handleChange}
                                className="border border-blue-500 p-2 rounded w-full"
                            />
                        ) : (
                            <p className="text-gray-600">{course[field]}</p>
                        )}
                    </div>
                ))}
            </div>

            <h3 className="text-xl font-semibold mt-6">Department</h3>
            <p className="text-gray-600">
                <strong>Department Name:</strong> {course.department.departmentName}
            </p>

            <h3 className="text-xl font-semibold mt-6">Instructor</h3>
            <p className="text-gray-600">
                <strong>Name:</strong> {course.instructor.firstName} {course.instructor.lastName}
            </p>
            <p className="text-gray-600">
                <strong>Email:</strong> {course.instructor.email}
            </p>

            {course.prerequisiteCourse && (
                <div>
                    <h3 className="text-xl font-semibold mt-6">Prerequisite Course</h3>
                    <p className="text-gray-600">
                        <strong>Course Name:</strong> {course.prerequisiteCourse.courseName}
                    </p>
                    <p className="text-gray-600">
                        <strong>Course Code:</strong> {course.prerequisiteCourse.courseCode}
                    </p>
                    <p className="text-gray-600">
                        <strong>Grade Level:</strong> {course.prerequisiteCourse.grade}
                    </p>
                    <p className="text-gray-600">
                        <strong>Semester:</strong> {course.prerequisiteCourse.semester.semesterName}
                    </p>
                </div>
            )}

            <h3 className="text-xl font-semibold mt-6">Semester</h3>
            <p className="text-gray-600">
                <strong>Year Level:</strong> {course.semester.yearLevel}
            </p>
            <p className="text-gray-600">
                <strong>Semester Name:</strong> {course.semester.semesterName}
            </p>

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
                                setEditedCourse(course);
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

            <Modal open={deleteModal} onClose={() => setDeleteModal(false)}>
                <h3 className="text-lg font-bold mb-4">Delete Course</h3>
                <p className="text-gray-600">Are you sure you want to delete this course?</p>
                <button
                    onClick={deleteCourse}
                    className="bg-red-600 text-white px-4 py-2 mt-2 rounded hover:bg-red-700"
                >
                    Delete Course
                </button>
            </Modal>
        </div>
    );
};

export default DetailedCourses;
