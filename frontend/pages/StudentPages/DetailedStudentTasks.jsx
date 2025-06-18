import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router'
import useAxiosPrivate from '../../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'

const DetailedStudentTasks = () => {
    const { taskId } = useParams()
    const axiosPrivate = useAxiosPrivate()
    const [task, setTask] = useState({})
    const [attachment, setAttachment] = useState(null)
    const [uploading, setUploading] = useState(false)

    useEffect(() => {
        const fetchTask = async () => {
            try {
                const response = await axiosPrivate.get(`/task/student/task/${taskId}`)
                setTask(response.data)
            } catch (error) {
                toast.error(`an error occurred while fetching task, ${error?.response?.data?.detail || error.message}`)
            }
        }
        fetchTask()
    }, [taskId, axiosPrivate])

    const handleDownload = () => {
        if (task.attachment) {
            window.open(task.attachment, '_blank')
        }
    }

    const handleFileChange = (e) => {
        if (e.target.files && e.target.files[0]) {
            setAttachment(e.target.files[0])
        }
    }

    const handleUpload = async () => {
        if (!attachment) return
        setUploading(true)
        const formData = new FormData()
        formData.append('attachment', attachment)
        try {
            await axiosPrivate.post(`/task/student/submit/${taskId}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            })
            toast.success('Solution uploaded successfully!')
            setAttachment(null)
        } catch (error) {
            toast.error(`Upload failed: ${error?.response?.data?.detail || error.message}`)
        } finally {
            setUploading(false)
        }
    }

    return (
        <div className="flex justify-center items-center min-h-[60vh] w-full">
            <div className="bg-white border border-gray-200 rounded-xl shadow-lg p-8 w-full max-w-xl">
                <h2 className="text-2xl font-bold text-gray-800 mb-2">{task.taskName}</h2>
                <p className="text-gray-600 mb-2">{task.description}</p>
                <div className="flex flex-wrap gap-4 mb-4">
                    <span className="text-gray-500 text-sm">Max Grade: <span className="font-semibold">{task.maxGrade}</span></span>
                    <span className="text-gray-500 text-sm">Deadline: <span className="font-semibold">{task.deadline}</span></span>
                    <span className="text-gray-400 text-xs">Created at: {task.createdAt ? new Date(task.createdAt).toLocaleString() : ''}</span>
                    <span className="text-gray-500 text-sm">Course ID: <span className="font-semibold">{task.courseId}</span></span>
                </div>
                {task.attachment && (
                    <button
                        onClick={handleDownload}
                        className="w-full mb-3 py-2 px-4 rounded-md border border-blue-600 text-blue-700 font-semibold bg-blue-50 hover:bg-blue-600 hover:text-white hover:border-blue-700 transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-blue-200"
                    >
                        View Attachment
                    </button>
                )}
                <div className="w-full flex flex-col gap-2 mt-2">
                    <label htmlFor="solution-upload" className="block text-gray-700 font-semibold mb-1">Upload Solution</label>
                    <input
                        id="solution-upload"
                        type="file"
                        className="w-full border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-gray-200"
                        onChange={handleFileChange}
                        disabled={uploading}
                    />
                    {attachment && (
                        <span className="text-sm text-gray-600 mt-1">Selected file: <span className="font-medium">{attachment.name}</span></span>
                    )}
                    <button
                        onClick={handleUpload}
                        className="w-full mt-2 py-2 px-4 rounded-md border border-green-600 text-green-700 font-semibold bg-green-50 hover:bg-green-600 hover:text-white hover:border-green-700 transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-green-200 disabled:opacity-60 disabled:cursor-not-allowed"
                        disabled={!attachment || uploading}
                    >
                        {uploading ? 'Uploading...' : 'Upload'}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default DetailedStudentTasks