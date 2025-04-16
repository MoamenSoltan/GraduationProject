import React, { useEffect, useState } from 'react'
import Modal from '../../components/Modal'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import dayjs from 'dayjs'
import useAxiosPrivate from '../../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'

const InstructorAnnouncements = () => {
  const [modal, setModal] = useState(false)

  const axiosPrivate = useAxiosPrivate()

  const [announcementData, setAnnouncementData] = useState({
    title: '',
    description: '',
    type: 'EXAM',
    courseId: '',
    announcementDate: '',
  })

  const [courses, setCourses] = useState([])

  const handleChange = (e) => {
    const { name, value } = e.target
    setAnnouncementData({ ...announcementData, [name]: value })
  }

  

  const handleFormSubmit = async (e) => {
    e.preventDefault()
    try {
      const response = await axiosPrivate.post(`/instructor/announcement`,announcementData)
      
      
      setModal(false)
      toast.success("announcement created successfully!")
    } catch (error) {
      toast.error(`an error occurred : ${error.message}`)
    }
  }

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const res = await axiosPrivate.get('/instructor/course')
        
        setCourses(res.data)
      } catch (err) {
        console.error('Error fetching courses:', err)
      }
    }

    fetchCourses()
    
    
  }, [])

  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Announcements</h2>

      <button
        onClick={() => setModal(true) }
        className="fixed bottom-5 right-5 p-4 w-28 h-28 rounded-full bg-blue-600 text-white hover:scale-105 transition-all text-2xl"
      >
        +
      </button>

      <Modal open={modal} onClose={() => setModal(false)}>
        <form className="w-full p-4 flex flex-col gap-4" onSubmit={handleFormSubmit}>
          <div>
            <label className="block text-sm font-medium text-gray-700">Title</label>
            <input
              name="title"
              type="text"
              className="textField w-full"
              placeholder="Enter announcement title"
              value={announcementData.title}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Description</label>
            <textarea
              name="description"
              className="textField w-full"
              rows="4"
              placeholder="Enter announcement description"
              value={announcementData.description}
              onChange={handleChange}
              required
            ></textarea>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Type</label>
            <select
              name="type"
              className="textField w-full"
              value={announcementData.type}
              onChange={handleChange}
              required
            >
              <option value="EXAM">Exam</option>
              <option value="EVENT">Event</option>
              <option value="ASSIGNMENT">Assignment</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Course</label>
            <select
              name="courseId"
              className="textField w-full"
              value={announcementData.courseId}
              onChange={handleChange}
              required
            >
              <option value="">Select a course</option>
              {courses.map((course) => (
                <option key={course.courseId} value={course.courseId}>
                  {course.courseName}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Announcement Date</label>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                value={
                  announcementData.announcementDate
                    ? dayjs(announcementData.announcementDate)
                    : null
                }
                onChange={(newValue) =>
                  setAnnouncementData({
                    ...announcementData,
                    announcementDate: newValue ? newValue.toISOString() : '',
                  })
                }
                format="YYYY-MM-DD"
              />
            </LocalizationProvider>
          </div>

          <button type="submit" className="custom-button bg-blue-600 text-white py-2 rounded mt-2 hover:bg-blue-700">
            Submit
          </button>
        </form>
      </Modal>
    </div>
  )
}

export default InstructorAnnouncements
