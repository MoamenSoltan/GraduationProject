import { BrowserRouter, Route, Routes } from "react-router"
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
/**
 * 
 * step 5 //bracket notation for loops , if we use user.key itll look for a value of key which doesnt exist (user.key is used for accessing known properties , bracket notation when the property is stored in a variable (in a loop))
 * 
 * --/ always use formData for api calls
 */

function App() {
 

  return (
    <div>
     
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Registration />} />
          <Route path="/registration" element={<Registration />} />
          {/* Add more routes here */}
          <Route path="/registration/splash" element={<Splash />} />
          <Route path="/registration/step1" element={<Step1 />} />
          <Route path="/registration/step2" element={<Step2 />} />
          <Route path="/registration/step3" element={<Step3 />} />
          <Route path="/registration/step4" element={<Step4 />} />
          <Route path="/registration/step5" element={<Step5 />} />
          <Route path="/registration/done" element={<Done />} />
          <Route path="/status" element={<Status />} />

          {/* or make a dashboard component , whithin this component add more dashboards */}
          <Route path="/studentDashboard" element={<StudentDashboard />} />
          <Route path="/instructorDashboard" element={<InstructorDashboard />} />
          <Route path="/adminDashboard" element={<AdminDashboard />} />
          






        </Routes>
      
      </BrowserRouter>
    </div>
  )
}

export default App
