import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';


const InstructorQuizSubmissionDetails = () => {
  const { quizId, studentId } = useParams();
  const [submission, setSubmission] = useState(null);
  const [essayScores, setEssayScores] = useState({});

  const axiosPrivate = useAxiosPrivate()

  useEffect(() => {
    const fetchSubmission = async () => {
      try {
        const res = await axiosPrivate.get(`/instructor/quiz/${quizId}/student/${studentId}/submission`);
        setSubmission(res.data);

        // Initialize essay score fields
        const initialScores = {};
        res.data.quiz.questions.forEach((q) => {
          if (q.type === 'essay') {
            initialScores[q.id] = '';
          }
        });
        setEssayScores(initialScores);
      } catch (error) {
        console.error('Error fetching submission:', error);
      }
    };

    fetchSubmission();
  }, [quizId, studentId]);

  const handleScoreChange = (questionId, value) => {
    setEssayScores((prev) => ({ ...prev, [questionId]: value }));
  };

  const handleSubmitFinalScore = async () => {
    try {
      await axiosPrivate.post(`/instructor/quiz/${quizId}/student/${studentId}/submit-score`, {
        essayScores,
      });
      alert('Final score submitted successfully.');
    } catch (error) {
      console.error('Error submitting final score:', error);
    }
  };

  if (!submission) {
    return <div className="text-center mt-10 text-gray-500">Loading submission...</div>;
  }

  return (
    <div className="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4 text-blue-700">
        Submission Details - {submission.student.name}
      </h2>

      {submission.quiz.questions.map((q, index) => {
        const studentAnswer = submission.answers[q.id];
        const isCorrect = q.type === 'mcq' && studentAnswer === q.correctAnswer;

        return (
          <div key={q.id} className="mb-6 border-b pb-4">
            <p className="font-medium">{index + 1}. {q.text}</p>

            {q.type === 'mcq' ? (
              <div className="mt-2">
                {q.options.map((option, i) => (
                  <div
                    key={i}
                    className={`border p-2 rounded mt-1 ${
                      i === q.correctAnswer ? 'bg-green-100 border-green-500 font-semibold' : ''
                    } ${
                      i === studentAnswer && i !== q.correctAnswer ? 'bg-red-100 border-red-500' : ''
                    }`}
                  >
                    {option}
                  </div>
                ))}
              </div>
            ) : (
              <div className="mt-2">
                <p><span className="font-semibold">Student Answer:</span> {studentAnswer || "Not answered"}</p>
                <label className="block mt-2 text-sm font-medium text-gray-700">
                  Score for this question:
                  <input
                    type="number"
                    value={essayScores[q.id] || ''}
                    onChange={(e) => handleScoreChange(q.id, e.target.value)}
                    className="mt-1 block w-24 px-2 py-1 border rounded"
                  />
                </label>
              </div>
            )}
          </div>
        );
      })}

      <div className="text-center mt-6">
        <button
          onClick={handleSubmitFinalScore}
          className="px-6 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition"
        >
          Submit Final Score
        </button>
      </div>
    </div>
  );
};

export default InstructorQuizSubmissionDetails;
