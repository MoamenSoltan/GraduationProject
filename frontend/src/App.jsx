import { BrowserRouter, Route, Routes, useNavigate } from "react-router"
import Registration from "../pages/Registration"
import Splash from "../pages/steps/Splash"
import Step1 from "../pages/steps/Step1"
import Step2 from "../pages/steps/Step2"
import Step3 from "../pages/steps/Step3"
import Step4 from "../pages/steps/Step4"
import Step5 from "../pages/steps/Step5"
import Done from "../pages/steps/Done"
import Status from "../pages/Status"
import StudentDashboard from "../pages/StudentDashboard"
import InstructorDashboard from "../pages/InstructorDashboard"
import AdminDashboard from "../pages/AdminDashboard"
import { useStateContext } from "../contexts/ContextProvider"
import { useEffect } from "react"
import ProtectedRoutes from "../components/ProtectedRoutes"
import { Toaster } from "react-hot-toast"
/**
 * e-commerce app for better understanding of filtering and query parameters
 * 
 * space-x-2 is a Tailwind CSS utility class that adds horizontal spacing (margin) between direct children of a flex container.
 * 
 * 
 * 
 * step 5 //bracket notation for loops , if we use user.key itll look for a value of key which doesnt exist (user.key is used for accessing known properties , bracket notation when the property is stored in a variable (in a loop))
 * 
 * --/ always use formData for api calls
 * 
 * 
 * conditional styling notes :
 * //style={{ width:activeMenu?"":""}}
// ${activeMenu?"ml-[300px]":""} `


revise courses component for better url parameters usage

flex-wrap 
array.filter 
map
array.includes //search
parseInt
slice

courses page

registration in sidebar for table advice


// conditional rendering based on route in studentDashboard

flex-wrap essential for overflow , revise profile , courses div specifically

note on search params 



 */

function App() {


  
  return (
    <div>

      
     
      <BrowserRouter>
     
        <Routes>
         
         {/* public routes */}

          <Route path="/" element={<Registration />} />
          <Route path="/registration" element={<Registration />} />
          <Route path="/registration/splash" element={<Splash />} />
          <Route path="/registration/step1" element={<Step1 />} />
          <Route path="/registration/step2" element={<Step2 />} />
          <Route path="/registration/step3" element={<Step3 />} />
          <Route path="/registration/step4" element={<Step4 />} />
          <Route path="/registration/step5" element={<Step5 />} />
          <Route path="/registration/done" element={<Done />} />
          <Route path="/status" element={<Status />} />

          <Route path="/studentDashboard/*" element={<StudentDashboard />} />
          <Route path="/instructorDashboard/*" element={<InstructorDashboard />} />
          <Route path="/adminDashboard/*" element={<AdminDashboard />} />
          
          {/* protected routes 
          <Route element={<ProtectedRoutes allowedRoles={['student']}/>}>
            <Route path="/studentDashboard/*" element={<StudentDashboard />} />
          </Route>
          

         <Route element={<ProtectedRoutes allowedRoles={['instructor']}/>}>
            <Route path="/instructorDashboard/*" element={<InstructorDashboard />} />
         </Route>

         <Route element={<ProtectedRoutes allowedRoles={['admin']}/>}>
            <Route path="/adminDashboard/*" element={<AdminDashboard />} />
         </Route> */}
          
          
          
          
          {/* 
          /* means that there are nested routes
           */}





        </Routes>
      
      </BrowserRouter>
      <Toaster/>
    </div>
  )
}

export default App
