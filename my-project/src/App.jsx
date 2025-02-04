import { BrowserRouter, Route, Routes } from "react-router"
import Registration from "../pages/Registration"
import Splash from "../pages/steps/Splash"
import Step1 from "../pages/steps/Step1"
import Step2 from "../pages/steps/Step2"
import Step3 from "../pages/steps/Step3"
import Step4 from "../pages/steps/Step4"
import Step5 from "../pages/steps/Step5"

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



        </Routes>
      
      </BrowserRouter>
    </div>
  )
}

export default App
