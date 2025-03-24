import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import SideBar from '../components/SideBar'
import NavBar from '../components/NavBar'
import { Route, Routes } from 'react-router'
import SubmissionRequests from './AdminPages/SubmissionRequests'
import CreateInstructor from './AdminPages/CreateInstructor'
import CreateCourse from './AdminPages/CreateCourse'
import DetailedSubmissionRequests from '../components/DetailedSubmissionRequests'
import DetailedInstructor from '../components/DetailedInstructor'
import DetailedCourses from '../components/DetailedCourses'
const AdminDashboard = () => {
  const {activeMenu}=useStateContext()

  return (
    <div>
      {
        <>
        {
             (activeMenu)&&
             
               <div className='md:w-[300px] fixed sidebar bg-white'>
                 <SideBar/>
               </div>
           
           }
            <div className=' top-0  bg-[#FAFBFB]  navbar w-full'>
               <NavBar/>
             </div>
       </>
      }

      <Routes>

        <Route path='/' element={<SubmissionRequests/>} />
        <Route path='/submission-Requests/' element={<SubmissionRequests/>} />
        <Route path='/submission-Requests/:id' element={<DetailedSubmissionRequests/>} />

        <Route path='/create-Instructor' element={<CreateInstructor/>}/>
        <Route path='/create-Instructor/:id' element={<DetailedInstructor/>}/>

        <Route path='/create-Course' element={<CreateCourse/>}/>
        <Route path='/create-Course/:id' element={<DetailedCourses/>}/>


      </Routes>
    </div>
  )
}

export default AdminDashboard