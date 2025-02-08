import React from 'react'
import { motion } from 'motion/react'
import { useNavigate } from 'react-router'
import { useStateContext } from '../../contexts/ContextProvider'
import Check from '../../data/check.svg'


const Done = () => {
    const navigate=useNavigate()
    const {Placeholder}=useStateContext()
  return (
    <motion.div
    initial={{ opacity: 0, scale: 0.9 }}
    animate={{ opacity: 1, scale: 1 }}
    exit={{ opacity: 0, scale: 0.9 }}
    transition={{ duration: 0.5 }}
     className='flex w-full mt-[30vh] justify-center items-center '>
      <div className='flex md:w-[80%] md:p-0 p-2 text-center  justify-center items-center flex-col gap-5 '>
         <img src={Check} className='w-[100px] h-[100px]' alt="check" />
         <h1 className='main-text'>Your information has been successfully submitted.</h1>
         
         <p className='sub-text '> Our admins will review your data, and you will receive an email with further instructions. Please check your inbox regularly.</p>
         
 
         
     </div>
    </motion.div>
  )
}

export default Done