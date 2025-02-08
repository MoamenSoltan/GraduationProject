import React from 'react'
import Check from '../../data/check.svg'
import { useStateContext } from '../../contexts/ContextProvider'
import { Link, useNavigate } from 'react-router'
import { motion } from 'motion/react'

const Splash = () => {
    const navigate=useNavigate()
    const {Placeholder}=useStateContext()

    

  return (
   <motion.div
   initial={{ opacity: 0, scale: 0.9 }}
   animate={{ opacity: 1, scale: 1 }}
   exit={{ opacity: 0, scale: 0.9 }}
   transition={{ duration: 0.5 }}
    className='flex w-full  justify-center items-start mt-28'>
     <div className='flex md:w-[45%] md:p-0 p-4  justify-center items-center flex-col gap-5 '>
        <img src={Check} className='w-[100px] h-[100px]' alt="check" />
        <h1 className='main-text md:w-full w-[80%] text-center '>Welcome to our <span className='underline'>{Placeholder}</span> community! </h1>
        <p className='sub-text'>Congratulations! you have created your account in {Placeholder} successfully!</p>
        <p className='sub-text'>Now you can start building your profile in the next few steps</p>
        <button onClick={()=>{navigate("/registration/step1")}} className='custom-button mx-auto w-full'>Get Started</button>

        
    </div>
   </motion.div>
  )
}

export default Splash