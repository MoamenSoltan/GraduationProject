import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

const QuizResults = () => {
  const { id: quizId } = useParams();
  const [result, setResult] = useState(null);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    const fetchResult = async () => {
      try {
        const res = await axiosPrivate.get(`/student/quiz/${quizId}/result`);
        setResult(res.data);
      } catch (err) {
        console.error('Failed to fetch quiz result:', err);
      }
    };

    fetchResult();
  }, [quizId]);

  if (!result) return <div className="text-center mt-10 text-gray-500">Loading your result...</div>;

  return (
    <div className="max-w-4xl mx-auto mt-10 p-6 bg-white shadow rounded">
      <h2 className="text-2xl font-bold mb-6 text-blue-700">{result.quiz.title} - Your Results</h2>

      {result.quiz.questions.map((q, index) => {
        const studentAnswer = result.answers[q.id];
        const isCorrect = q.type === 'mcq' && studentAnswer === q.correctAnswer;

        return (
          <div key={q.id} className="mb-6 border-b pb-4">
            <p className="font-semibold mb-2">{index + 1}. {q.text}</p>

            {q.type === 'mcq' ? (
              <div className="space-y-1">
                {q.options.map((option, idx) => (
                  <div
                    key={idx}
                    className={`p-2 rounded border ${
                      idx === q.correctAnswer
                        ? 'bg-green-100 border-green-500 font-medium'
                        : idx === studentAnswer
                        ? 'bg-red-100 border-red-500'
                        : 'border-gray-300'
                    }`}
                  >
                    {option}
                  </div>
                ))}
              </div>
            ) : (
              <div className="mt-2">
                <p><strong>Your Answer:</strong> {studentAnswer || 'Not answered'}</p>
                <p><strong>Score:</strong> {result.essayScores[q.id] ?? 'Not graded yet'}</p>
              </div>
            )}
          </div>
        );
      })}

      <div className="mt-6 text-lg font-bold text-center text-green-700">
        Final Score: {result.totalScore} / {result.maxScore}
      </div>
    </div>
  );
};

export default QuizResults;
