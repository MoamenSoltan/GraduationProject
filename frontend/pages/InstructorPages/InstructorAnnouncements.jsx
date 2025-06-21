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
  const [reFetch, setreFetch] = useState(false)
  const axiosPrivate = useAxiosPrivate()

  const [announcementData, setAnnouncementData] = useState({
    title: '',
    description: '',
    type: 'EXAM',
    courseId: '',
    announcementDate: '',
  })

  const [courses, setCourses] = useState([])
  const [announcements,setAnnouncements] = useState([])

  const handleChange = (e) => {
    const { name, value } = e.target
    setAnnouncementData({ ...announcementData, [name]: value })
  }

  

  const handleFormSubmit = async (e) => {
    e.preventDefault()
    try {
      await axiosPrivate.post(`/instructor/announcement`,announcementData)
      setModal(false)
      setreFetch(!reFetch)
      toast.success("announcement created successfully!")
    } catch (error) {
      toast.error(`an error occurred : ${error.message}`)
    }
  }

  useEffect(() => {
    const fetchAnnouncements = async ()=>{
      try {
        const response = await axiosPrivate.get("/instructor/announcement")
        setAnnouncements(response.data)
        console.log("announcements fetched :",response.data);
        
      } catch (error) {
        toast.error(`an error occurred : ${error}`)
      }
    }

    const fetchCourses = async () => {
      try {
        const res = await axiosPrivate.get('/instructor/course')
        
        setCourses(res.data)
        console.log("fetched courses",res.data);
        
      } catch (err) {
        console.error('Error fetching courses:', err)
      }
    }

    fetchAnnouncements()

    fetchCourses()
    
    
  }, [reFetch])

  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Announcements</h2>
      {announcements.length === 0 && (
        <h1 className="text-gray-400 text-center text-lg w-full mb-6">No announcements yet.</h1>
      )}



      <div className="flex flex-wrap gap-4 justify-start">
  {announcements.map((announcement) => (
    <div
      key={announcement.announcementId}
      className="bg-white rounded-lg shadow-sm border border-gray-200 p-4 w-full sm:w-[48%] md:w-[30%] lg:w-[23%] transition-transform hover:scale-105"
    >
      <div className="flex justify-between items-center mb-2">
        <h3 className="text-lg font-semibold text-gray-800 truncate">{announcement.title}</h3>
        <span className="text-xs font-medium text-blue-600 bg-blue-100 px-2 py-0.5 rounded-full uppercase">
          {announcement.type}
        </span>
      </div>

      <p className="text-sm text-gray-700 mb-3 line-clamp-2">{announcement.description}</p>

      <div className="text-xs text-gray-600 space-y-1">
        <p><strong>Course:</strong> {announcement.courseName}</p>
        <p><strong>Instructor:</strong> {announcement.instructorName}</p>
        <p><strong>Date:</strong> {dayjs(announcement.announcementDate).format('MMMM D, YYYY')}</p>
      </div>
    </div>
  ))}
  
</div>
<button
        onClick={() => setModal(true)}
        className="block mx-auto mt-8 p-4 w-20 h-20 rounded-full bg-blue-600 text-white text-3xl shadow-lg hover:scale-110 transition-all"
        title="Add Material"
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
