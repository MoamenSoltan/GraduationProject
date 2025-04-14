import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import SideBar from '../components/SideBar'
import NavBar from '../components/NavBar'
import { Route, Routes } from 'react-router'
import AssignedCourses from './InstructorPages/AssignedCourses'
import InstructorAnnouncements from './InstructorPages/InstructorAnnouncements'
import Quizzes from './InstructorPages/Quizzes'

const InstructorDashboard = () => {
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

<Route path='/' element={<AssignedCourses/>} />
<Route path='/assigned-Courses/' element={<AssignedCourses/>} />
{/* <Route path='/submission-Requests/:id' element={<DetailedSubmissionRequests/>} /> */}

<Route path='/announcements' element={<InstructorAnnouncements/>}/>
{/* <Route path='/create-Instructor/:id' element={<DetailedInstructor/>}/> */}

<Route path='/quizzes' element={<Quizzes/>}/>
{/* <Route path='/create-Course/:id' element={<DetailedCourses/>}/> */}


</Routes>

    </div>
  )
}

export default InstructorDashboard