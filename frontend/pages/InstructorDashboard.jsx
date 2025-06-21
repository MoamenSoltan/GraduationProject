import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import SideBar from '../components/SideBar'
import NavBar from '../components/NavBar'
import { Route, Routes } from 'react-router'
import AssignedCourses from './InstructorPages/AssignedCourses'
import InstructorAnnouncements from './InstructorPages/InstructorAnnouncements'
import Quizzes from './InstructorPages/Quizzes'
import DetailedQuizzes from '../components/DetailedQuizzes'
import InstructorQuizSubmissionDetails from '../components/InstructorQuizSubmissionDetails'
import InstructorTasks from "../pages/InstructorPages/InstructorTasks"
import DetailedInstructorCourses from './InstructorPages/DetailedInstructorCourses'
import StudentInCourse from './InstructorPages/StudentInCourse'
import DetailedTasks from './InstructorPages/DetailedTasks'
import TaskSubmissions from './InstructorPages/TaskSubmissions'
import ReviewTasks from './InstructorPages/ReviewTasks'
import QuizzesForaCourse from './InstructorPages/QuizzesForaCourse'
import QuizSubmissions from '../components/QuizSubmissions'
import InstructorMaterial from './InstructorPages/InstructorMaterial'
import InstructorMaterialForACourse from './InstructorPages/InstructorMaterialForACourse'
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
<Route path='/assigned-Courses/:id' element={<DetailedInstructorCourses/>} />
<Route path='/assigned-Courses/:id/students/:stdId' element={<StudentInCourse/>} />


{/* <Route path='/submission-Requests/:id' element={<DetailedSubmissionRequests/>} /> */}

<Route path='/announcements' element={<InstructorAnnouncements/>}/>
{/* <Route path='/create-Instructor/:id' element={<DetailedInstructor/>}/> */}

<Route path='/quizzes' element={<Quizzes/>}/>
<Route path='/quizzes/:quizId/course/:courseId' element={<DetailedQuizzes/>}/>
<Route path='/quizzes/all/:courseId' element={<QuizzesForaCourse/>}/>


{/* TODO: might need refinements */}
<Route path='/quizzes/:quizId/course/:courseId/submissions' element={<QuizSubmissions/>}/>
<Route path='/quizzes/:quizId/course/:courseId/student/:studentId/submissions' element={<InstructorQuizSubmissionDetails/>}/>

<Route path='/tasks' element={<InstructorTasks/>}/>
<Route path='/tasks/:id' element={<DetailedTasks/>}/>
<Route path='/tasks/:taskId/submissions' element={<TaskSubmissions/>}/>
<Route path='/tasks/:taskId/submissions/:submissionId/:courseId' element={<ReviewTasks/>}/>
<Route path='/material' element={<InstructorMaterial/>}/>
<Route path='/material/:courseId' element={<InstructorMaterialForACourse/>}/>










</Routes>

    </div>
  )
}

export default InstructorDashboard