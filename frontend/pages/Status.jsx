import React from 'react'
import { motion } from 'motion/react'
import { useStateContext } from '../contexts/ContextProvider'
import { useNavigate } from 'react-router'
import accepted from "../data/accepted.svg"
import rejected from "../data/rejected.svg"

const Status = () => {
    const {user}=useStateContext()
    const navigate = useNavigate()
    
    return (
        <motion.div
        initial={{ opacity: 0, scale: 0.9 }}
        animate={{ opacity: 1, scale: 1 }}
        exit={{ opacity: 0, scale: 0.9 }}
        transition={{ duration: 0.5 }}
         className='flex w-full mt-[30vh] justify-center items-center text-center '>
          <div className='flex md:w-[80%] md:p-0 p-4  justify-center items-center flex-col gap-5 '>
            
            
        {
            user.status==="accepted"? (
              <div className='flex flex-col items-center'>
                <img src={accepted} alt='accepted' className='w-[100px] mb-10'/>
                <h1 className='main-text'>Congratulations! 🎉 Your submission has been reviewed and approved by our admins.</h1>
                <p className='sub-text'>You can now access your dashboard to view your profile, track your progress, and explore available features.</p>
                <button onClick={()=>navigate("/studentDashboard")} className='custom-button mx-auto '>Go to Dashboard</button>
              </div>
            ) : (
              <div className='flex flex-col justify-center items-center'> 
                <img src={rejected}  alt='rejected' className='w-[100px] mb-10'/>
                <h1 className='main-text'>Unfortunately, your submission has been rejected ❌ . </h1>
                <p className='sub-text'> You may review the requirements and try to register again.</p>
                <button onClick={()=>navigate("/registration/")} className='custom-button mx-auto '>Register</button>
              </div>
            )
        }
             
     
             
         </div>
        </motion.div>
      )
}

export default Status