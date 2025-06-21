import React, { useEffect, useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router'
import useAxiosPrivate from '../../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'
import Modal from '../../components/Modal'
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider"
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"
import { DatePicker } from "@mui/x-date-pickers/DatePicker"
import dayjs from "dayjs"

const InstructorTasks = () => {
  const [tasks, setTasks] = useState([])
  const [modal, setModal] = useState(false)
  const [courses, setCourses] = useState([])
  const [reFetch, setreFetch] = useState(false)
  const [loading, setloading] = useState(false)
  const [currentPage,setCurrentPage]=useState(1)

  const cardsPerPage = 6

  const lastIndexInPage = currentPage * cardsPerPage
  const firstIndexInPage = lastIndexInPage - cardsPerPage
   
  const currentTasks = tasks.slice(firstIndexInPage,lastIndexInPage)

  const prevPage = ()=>{
    if(currentPage>1){
      setCurrentPage(prev=>prev-1)
    }
  }
  const nextPage = ()=>{
    if(currentPage<(Math.ceil(tasks.length/cardsPerPage)))
      setCurrentPage(prev=>prev+1)
  }
  
  const [formData, setFormData] = useState({
    taskName: '',
    maxGrade: '',
    description: '',
    attachment: null,
    isActive: 'true',
    deadline: '',
    courseId: ''
  })
  // TODO: form data and fix file and api

  // const selectCourses = courses.filter((course)=>(tasks.some((task)=>(
  //   course.courseId===task.courseId
  // ))))


  const axiosPrivate = useAxiosPrivate()

  const [searchParams,setSearchParams] = useSearchParams()
  const selectedCourse = searchParams.get('courseId') || ""
    const selectedSort = searchParams.get('sortDir') || ""
    // TODO: dont forget ||''
  useEffect(() => {

    
//params for fetching
    const params = new URLSearchParams()

    if (selectedCourse) params.append('courseId',selectedCourse)
    if (selectedSort) params.append('sortDir',selectedSort)



    const fetchTasks = async () => {
      try {
        const response = await axiosPrivate.get(`/task/instructor?${params.toString()}`)
        // TODO: dont forget tostring() , and ?
        setTasks(response.data)
      } catch (error) {
        
      }
    }
    
    
    const fetchCourses = async()=>{
      try {
        const response = await axiosPrivate.get(`/instructor/course`)
        setCourses(response.data)

      } catch (error) {
        toast.error(`An error occurred: ${error.message}`)
      }
    }
    fetchCourses()
    console.log("fetched courses :",courses);
    
    fetchTasks()
  }, [reFetch,selectedCourse,selectedSort])
  // TODO: dont forget to add selectedCourse and selectedSort to dependency array

  const handleFilterChange = (filterName,value)=>{
    //params to change the searchparams not used in api call
    const newParams = new URLSearchParams(searchParams)
    //if (value) means , if a select all, means its value is "" which is falsy , therefore remove the filter name , otherwise will be 
    // api?filterName="" , because its an empty string ,we want to remove the filtername if all is selected
    if (value)
      newParams.set(filterName,value)
    else
    newParams.delete(filterName)

    setSearchParams(newParams) 
    // TODO: dont forget line 78
  }

  const handleSubmit = async (e) => {

    e.preventDefault()

    // Optional: validate required fields
    if (!formData.taskName || !formData.maxGrade || !formData.courseId || !formData.deadline) {
      return toast.error("Please fill out required fields.")
    }

    try {
     
    if(loading)
      return
    setloading(true)
      const newformData = new FormData()

      for (const key in formData) {
        if(key!=="attachment")
          newformData.append(key, formData[key])
        else 
        if(key==="attachment"&&formData[key]!==null)
          newformData.append("attachment", formData["attachment"])
      }
      Object.entries(formData).forEach(([key, value]) => {
        console.log(`${key}: ${value}`);
      });
      
      const response = await axiosPrivate.post("/task/instructor/create", newformData,{
        headers:{
          'Content-Type': 'multipart/form-data',
        }
      })
      toast.success("Task added successfully!")
      setModal(false)
      setreFetch(prev=>!prev)
    } catch (error) {
      Object.entries(formData).forEach(([key, value]) => {
        console.log(`${key}: ${value}`);
      });
      toast.error(`Error: ${error}`)
      console.log("an error occurred while creating task",error);
      
    }finally{
      setloading(false)
    }
  }
const navigate = useNavigate()
  

  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
      <div className='w-full flex justify-between'>
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Tasks</h2>


      <div className='flex gap-2'>
        <select value={selectedCourse} onChange={(e)=>handleFilterChange("courseId",e.target.value)}  className=' border-2 rounded-md outline-none' name="courseId">
          <option value="">All Courses</option>
          {
            courses.map((course) => (
              <option key={course.courseId} value={course.courseId}>{course.courseName}</option>
            ))
          }
        </select>
        <select value={selectedSort} name="sortDir" onChange={(e)=>handleFilterChange("sortDir",e.target.value)} className='w-[100px] border-2 rounded-md outline-none'>

          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>
      </div>
      
      </div>
      {
        tasks.length===0&&
        <h1 className='text-gray-400 text-xl  text-center'>no tasks added yet.</h1>
      }
      <div className="grid gap-4 grid-cols-1 md:grid-cols-2 lg:grid-cols-3 mt-8">
  {currentTasks.map((task) => (
    <div onClick={()=>navigate(`/instructorDashboard/Tasks/${task.id}`)} key={task.id} className="p-4 bg-white rounded-xl shadow-md border hover:shadow-lg transition">
      <h3 className="text-xl font-semibold text-gray-800 mb-2">{task.taskName}</h3>
      <h3 className="font-semibold text-gray-800 mb-2">
  Course: {courses.find((course)=>(course.courseId===task.courseId))?.courseName||"unknown"}
</h3>

      <p className="text-sm text-gray-600 mb-2">{task.description}</p>
      
      <p className="text-sm text-gray-500 mb-1">
        <strong>Max Grade:</strong> {task.maxGrade}
      </p>
      <p className="text-sm text-gray-500 mb-1">
        <strong>Deadline:</strong> {task.deadline}
      </p>
      <p className="text-sm text-gray-500 mb-1">
        <strong>Status:</strong>{" "}
        <span className={task.active ? "text-green-600 font-medium" : "text-red-600 font-medium"}>
          {task.active ? "Active" : "Inactive"}
        </span>
      </p>
     
      <p className="text-xs text-gray-400 mt-2">Created at: {new Date(task.createdAt).toLocaleString()}</p>
    </div>
  ))}
    
</div>

       {
      (tasks.length / cardsPerPage) >=1 &&
      <div className="flex justify-center items-center space-x-4 mt-6">
                <button 
                    onClick={prevPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === 1}
                >
                    Previous
                </button>
                <span className="text-lg font-semibold">
                    Page {currentPage} of {Math.ceil(tasks.length / cardsPerPage)}
                </span>
                <button 
                    onClick={nextPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === Math.ceil(tasks.length / cardsPerPage)}
                >
                    Next
                </button>
            </div>
     }

<button
        onClick={() => setModal(true)}
        className="block mx-auto mt-8 p-4 w-20 h-20 rounded-full bg-blue-600 text-white text-3xl shadow-lg hover:scale-110 transition-all"
        title="Add Material"
      >
        +
      </button>

      <Modal open={modal} onClose={() => setModal(false)}>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4 p-4">
          <input
            type="text"
            className="textField"
            placeholder="Task Name"
            value={formData.taskName}
            onChange={(e) => setFormData({ ...formData, taskName: e.target.value })}
          />

          <input
            type="number"
            className="textField"
            placeholder="Max Grade"
            step="0.1"
            value={formData.maxGrade}
            onChange={(e) => setFormData({ ...formData, maxGrade: e.target.value })}
          />

          <textarea
            className="textField"
            placeholder="Description"
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
          />

          <input type="file" accept='.docx,.pdf,.csv' name="personalImage" className="textField mt-3 file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"  onChange={(e)=>setFormData({...formData,attachment:e.target.files[0]})} />

          <select
            className="textField"
            value={formData.isActive}
            onChange={(e) => setFormData({ ...formData, isActive: e.target.value })}
          >
            <option value="true">Active</option>
            <option value="false">Inactive</option>
          </select>

          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Deadline"
              value={formData.deadline ? dayjs(formData.deadline) : null}
              onChange={(newValue) =>
                setFormData({
                  ...formData,
                  deadline: newValue ? newValue.format("YYYY-MM-DD") : "",
                })
              }
              format="YYYY-MM-DD"
            />
          </LocalizationProvider>

         <select name="courseId" id="" className='w-full p-2 border rounded mb-2' onChange={(e)=>{setFormData({...formData,courseId:e.target.value})}}>
          <option value="">Select Course</option>
          {courses?.map((course) => (
            <option key={course.courseId} value={course.courseId}>{course.courseName}</option>
          ))}
         </select>

          <button type="submit" className="custom-button">
            {loading?"loading...":"Submit Task"}
          </button>
        </form>
      </Modal>
    </div>
  )
}

export default InstructorTasks
