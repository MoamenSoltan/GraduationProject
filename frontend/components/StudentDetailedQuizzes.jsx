import React, { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router'
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'
import Modal from '../components/Modal'

const StudentDetailedQuizzes = () => {
  const { id } = useParams()
  const axiosPrivate = useAxiosPrivate()
  const navigate = useNavigate()

  const [quiz, setQuiz] = useState(null)
  const [modal, setModal] = useState(false)

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const res = await axiosPrivate.get(`/student/quiz/${id}`)
        setQuiz(res.data)
      } catch (err) {
        toast.error("Failed to fetch quiz details")
      }
    }

    fetchQuiz()
  }, [id])

  const handleStartQuiz = () => {
    navigate(`/studentDashboard/Quizzes/${id}/start`, { state: { quiz } })
  }

  if (!quiz) return <div>Loading quiz details...</div>

  return (
    <div className="w-full md:w-[80%] m-auto mt-10">
      <h1 className="text-3xl font-bold">{quiz.name}</h1>
      <p className="text-gray-600 mt-2">{quiz.description}</p>
      <p className="text-sm text-gray-500 mt-1">Number of questions: {quiz.questions.length}</p>

      <button
        onClick={() => setModal(true)}
        className="mt-6 px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Start Quiz
      </button>

      <Modal open={modal} onClose={() => setModal(false)}>
        <div className="p-4 space-y-4">
          <h2 className="text-xl font-semibold">Ready to start?</h2>
          <p>This quiz contains {quiz.questions.length} questions.</p>
          <div className="flex justify-end gap-4">
            <button onClick={() => setModal(false)} className="text-gray-600 hover:underline">Not Now</button>
            <button onClick={handleStartQuiz} className="px-4 py-2 bg-green-600 text-white rounded">Start Quiz</button>
          </div>
        </div>
      </Modal>
    </div>
  )
}

export default StudentDetailedQuizzes
