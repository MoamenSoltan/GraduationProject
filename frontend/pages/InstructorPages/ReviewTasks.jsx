import React, { useState } from 'react'
import { useLocation, useParams, useNavigate } from 'react-router-dom'
import useAxiosPrivate from '../../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'

const ReviewTasks = () => {
  const location = useLocation()
  const navigate = useNavigate()
  const { taskId, submissionId,courseId } = useParams()

  const attachment = location.state?.attachment

  const [grade, setGrade] = useState('')
  const [loading, setLoading] = useState(false)

  const axiosPrivate = useAxiosPrivate()

  const handleAttachment = async () => {
    try {
      const response = await axiosPrivate.get(attachment, { responseType: 'blob' })
      const file = new Blob([response.data], { type: response.headers['content-type'] })
      const fileURL = URL.createObjectURL(file)

      const link = document.createElement('a')
      link.href = fileURL
      link.download = attachment.split('/').pop()
      document.body.appendChild(link)
      link.click()
      link.remove()

      URL.revokeObjectURL(fileURL)
    } catch (err) {
      toast.error('Failed to download attachment.')
    }
  }

  const handleSubmitGrade = async () => {
    if (!grade || isNaN(grade)) {
      toast.error("Please enter a valid grade.")
      return
    }

    try {
      setLoading(true)
      await axiosPrivate.put(`/task/instructor/submissions/${courseId}/${taskId}/grade`, {
        submissionId,
        grade: parseFloat(grade),
      })
      toast.success("Grade submitted successfully!")
      navigate(-1) // go back to previous page
    } catch (err) {
      toast.error("Failed to submit grade.")
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-xl mx-auto mt-10 bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Review Task Submission</h2>

      <div className="mb-4">
        <p className="text-gray-700"><strong>Task ID:</strong> {taskId}</p>
        <p className="text-gray-700"><strong>Submission ID:</strong> {submissionId}</p>
      </div>

      {attachment && (
        <button
          onClick={handleAttachment}
          className="mb-6 px-4 py-2 bg-blue-600 text-white font-medium rounded hover:bg-blue-700 transition"
        >
          View Attachment
        </button>
      )}

      <div className="mb-4">
        <label className="block text-gray-700 font-medium mb-2" htmlFor="grade">
          Grade
        </label>
        <input
          id="grade"
          type="number"
          min="0"
          step="0.1"
          value={grade}
          onChange={(e) => setGrade(e.target.value)}
          className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Enter grade"
        />
      </div>

      <button
        onClick={handleSubmitGrade}
        disabled={loading}
        className={`w-full py-2 rounded text-white font-medium ${
          loading ? 'bg-gray-500' : 'bg-green-600 hover:bg-green-700'
        } transition`}
      >
        {loading ? 'Submitting...' : 'Submit Grade'}
      </button>
    </div>
  )
}

export default ReviewTasks
