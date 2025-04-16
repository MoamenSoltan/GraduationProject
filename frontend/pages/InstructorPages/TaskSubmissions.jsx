import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";

const TaskSubmissions = () => {
  const { taskId } = useParams();
  const location = useLocation();
  const courseId = location.state?.courseId;
  const axiosPrivate = useAxiosPrivate();

  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    console.log("the courseId is", courseId);
    const fetchSubmissions = async () => {
      if (!courseId || !taskId || loading) return;
      setLoading(true);
      try {
        const response = await axiosPrivate.get(
          `/task/instructor/submissions/${courseId}/${taskId}`
        );
        setSubmissions(response.data);
      } catch (err) {
        setError("Failed to load submissions.");
        toast.error("Error loading submissions");
      } finally {
        setLoading(false);
      }
    };

    fetchSubmissions();
  }, [courseId, taskId, axiosPrivate]);

  if (loading)
    return (
      <div className="text-center mt-10 text-lg">Loading submissions...</div>
    );
  if (error)
    return <div className="text-center mt-10 text-red-600">{error}</div>;

  return (
    <div className="max-w-5xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-gray-800">
        Task Submissions
      </h2>

      {submissions.length === 0 ? (
        <p className="text-gray-600">No submissions available for this task.</p>
      ) : (
        <div className="overflow-x-auto">
          <table className="w-full border border-gray-300 text-left text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="py-2 px-4 border-b">Student Email</th>
                <th className="py-2 px-4 border-b">Submitted At</th>
                <th className="py-2 px-4 border-b">Grade</th>
                <th className="py-2 px-4 border-b">Attachment</th>
                <th className="py-2 px-4 border-b">Review</th>
              </tr>
            </thead>
            <tbody>
              {submissions.map((submission) => (
                <tr key={submission.id} className="hover:bg-gray-50">
                  <td className="py-2 px-4 border-b">
                    {submission.studentEmail}
                  </td>
                  <td className="py-2 px-4 border-b">
                    {submission.submittedAt}
                  </td>
                  <td className="py-2 px-4 border-b">
                    {submission.grade ?? "N/A"}
                  </td>
                  <td className="py-2 px-4 border-b">
                    {submission.attachment ? (
                      <a
                        href={submission.attachment}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-blue-600 hover:underline"
                      >
                        View
                      </a>
                    ) : (
                      "No attachment"
                    )}
                  </td>
                  <td className="py-2 px-4 border-b">
                    <button
                      onClick={() =>
                        navigate(
                          `/instructorDashboard/Tasks/${taskId}/submissions/${submission.id}/${courseId}`,
                          { state: { attachment: submission.attachment } }
                        )
                      }
                      className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-1.5 px-4 rounded-md transition duration-200 ease-in-out shadow-sm"
                    >
                      Review Task
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default TaskSubmissions;
