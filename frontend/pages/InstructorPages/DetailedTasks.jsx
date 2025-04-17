import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import useAxiosPrivate from '../../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'
import Modal from "../../components/Modal"

const DetailedTasks = () => {
  const { id } = useParams()
  const axiosPrivate = useAxiosPrivate()
  const [task, setTask] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [deleteModal, setdeleteModal] = useState(false)


  const navigate = useNavigate()

  useEffect(() => {
    const fetchTaskDetails = async () => {
      try {
        const response = await axiosPrivate.get(`/task/instructor/${id}`)
        setTask(response.data)
        setLoading(false)
        console.log("task details : ",response.data);
        
      } catch (err) {
        setError('Failed to fetch task details.')
        toast.error('Error fetching task details')
        setLoading(false)
      }
    }

    fetchTaskDetails()
  }, [id, axiosPrivate])

  const handleAttachment = async () => {
    try {
      const response = await axiosPrivate.get(task.attachment, {
        responseType: 'blob', // important!
      })
  
      const file = new Blob([response.data], { type: response.headers['content-type'] })
      const fileURL = URL.createObjectURL(file)
  
      const link = document.createElement('a')
      link.href = fileURL
      link.download = task.attachment.split('/').pop() // get filename from URL
      document.body.appendChild(link)
      link.click()
      link.remove()
  
      URL.revokeObjectURL(fileURL)
    } catch (error) {
      toast.error('Failed to open/download the attachment.')
      console.error('Attachment error:', error)
    }
    // window.open(task.attachment,"_blank")
  }
  
  const handleDelete=async()=>{
    try {
      const response = await axiosPrivate.delete(`/task/instructor/${id}`)
      navigate("/instructorDashboard/Tasks")
    } catch (error) {
      toast.error(`an error occurred while deleting the task ${error}`)
    }
  }
  
  

  if (loading) return <div className="text-center mt-10 text-lg">Loading task details...</div>
  if (error) return <div className="text-center mt-10 text-red-600">{error}</div>

  return (
    <div className="max-w-3xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-3xl font-bold mb-4 text-gray-800">{task.taskName}</h2>

      <p className="text-gray-700 mb-2"><strong>Description:</strong> {task.description || 'No description provided.'}</p>

      <p className="text-gray-700 mb-2"><strong>Max Grade:</strong> {task.maxGrade}</p>

      <p className="text-gray-700 mb-2"><strong>Deadline:</strong> {task.deadline}</p>

      <p className="text-gray-700 mb-2">
        <strong>Status:</strong>{" "}
        <span className={task.active ? 'text-green-600 font-medium' : 'text-red-600 font-medium'}>
          {task.active ? 'Active' : 'Inactive'}
        </span>
      </p>

      {task.attachment && (
         <button
                onClick={()=>handleAttachment()}
                className="px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded-lg hover:bg-blue-700 transition"
              >
                attachment
              </button>
      )}
       <div className='flex w-[80%] mt-5 gap-5 items-center'>
       <button
                onClick={()=>setdeleteModal(true)}
                className="px-4 py-2 bg-red-600 text-white text-sm font-medium rounded-lg hover:bg-red-700 transition"
              >
                Delete task
              </button>
              <button
                onClick={()=>navigate(`/instructorDashboard/Tasks/${id}/submissions`,{state:{courseId:task.courseId}})}
                className="px-4 py-2 bg-yellow-600 text-white text-sm font-medium rounded-lg hover:bg-yellow-700 transition"
              >
                View task submissions
              </button>
       </div>

      <p className="text-sm text-gray-500 mt-4">Created at: {new Date(task.createdAt).toLocaleString()}</p>

      <p className="text-sm text-gray-500">Course ID: {task.courseId}</p>

      <Modal open={deleteModal} onClose={()=>setdeleteModal(false)}>
      <div className="w-full p-6">
          <h3 className="text-xl w-full font-bold mb-4">Are you sure you want to delete this task?</h3>
          <div className="flex justify-center gap-4">
            <button
              onClick={()=>handleDelete()}
              className="px-4 py-2 bg-red-600 text-white font-medium rounded-lg hover:bg-red-700"
            >
              Yes, Delete
            </button>
            <button
              onClick={() => setdeleteModal(false)}
              className="px-4 py-2 bg-gray-600 text-white font-medium rounded-lg hover:bg-gray-700"
            >
              No, Cancel
            </button>
          </div>
        </div>
      </Modal>
    </div>
  )
}

export default DetailedTasks
