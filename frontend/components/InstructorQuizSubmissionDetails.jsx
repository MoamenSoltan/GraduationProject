import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

const InstructorQuizSubmissionDetails = () => {
  const { courseId, quizId, studentId } = useParams();
  const axiosPrivate = useAxiosPrivate();

  const [submission, setSubmission] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchSubmissionDetails = async () => {
      try {
        const response = await axiosPrivate.get(
          `/instructor/quiz/${quizId}/course/${courseId}/student/${studentId}/submission`
        );
        setSubmission(response.data);
      } catch (err) {
        setError('Failed to load submission details.');
      } finally {
        setLoading(false);
      }
    };

    fetchSubmissionDetails();
  }, [courseId, quizId, studentId]);

  if (loading) return <div className="text-center py-6 text-[#0096C1]">Loading submission details...</div>;
  if (error) return <div className="text-center py-6 text-red-500">{error}</div>;
  if (!submission) return <div className="text-center py-6 text-gray-600">No submission found.</div>;

  return (
    <div className="w-[90%] max-w-3xl mx-auto mt-10 bg-white shadow-lg rounded-xl p-6 border border-gray-200">
      <h2 className="text-2xl font-bold text-[#0096C1] mb-4 border-b pb-2">Quiz Submission Details</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-gray-800 mb-6">
        <div><span className="font-medium text-[#0096C1]">Student ID:</span> {submission.studentId}</div>
        <div><span className="font-medium text-[#0096C1]">Quiz ID:</span> {submission.quizId}</div>
        <div><span className="font-medium text-[#0096C1]">Quiz Title:</span> {submission.quizTitle}</div>
        <div><span className="font-medium text-[#0096C1]">Total Questions:</span> {submission.totalQuestions}</div>
        <div><span className="font-medium text-[#0096C1]">MCQ Score:</span> {submission.mcqScore}</div>
        <div><span className="font-medium text-[#0096C1]">Submitted At:</span> {new Date(submission.submittedAt).toLocaleString()}</div>
      </div>

      <div className="mb-6">
        <h3 className="text-lg font-semibold text-[#0096C1] mb-2">MCQ Answers</h3>
        {submission.answers_MCQ && Object.keys(submission.answers_MCQ).length > 0 ? (
          <ul className="space-y-2 pl-2">
            {Object.entries(submission.answers_MCQ).map(([questionId, answer], index) => (
              <li key={index} className="bg-gray-50 p-3 rounded border border-gray-200">
                <strong className="text-black">Q{questionId}:</strong> <span className="text-gray-700">{answer}</span>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-600">No MCQ answers submitted.</p>
        )}
      </div>

      <div>
        <h3 className="text-lg font-semibold text-[#0096C1] mb-2">Short Answers</h3>
        {submission.answers_short && submission.answers_short.length > 0 ? (
          <ul className="space-y-2 pl-2">
            {submission.answers_short.map((answer, index) => (
              <li key={index} className="bg-gray-50 p-3 rounded border border-gray-200">
                <span className="text-gray-700">{answer}</span>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-600">No short answers submitted.</p>
        )}
      </div>
    </div>
  );
};

export default InstructorQuizSubmissionDetails;
