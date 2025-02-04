import React from 'react'
import Check from '../../data/check.svg'
import { useStateContext } from '../../contexts/ContextProvider'
import { Link, useNavigate } from 'react-router'

const Splash = () => {
    const navigate=useNavigate()
    const {Placeholder}=useStateContext()
  return (
   <div className='flex w-full  justify-center items-start mt-28'>
     <div className='flex w-[30%]  justify-center items-center flex-col gap-5 '>
        <img src={Check} className='w-[100px] h-[100px]' alt="check" />
        <h1 className='main-text'>Welcome to our <span className='underline'>{Placeholder}</span> community! </h1>
        <p className='sub-text'>Congratulations! you have created your account in {Placeholder} successfully!</p>
        <p className='sub-text'>Now you can start building your profile in the next few steps</p>
        <button onClick={()=>{navigate("/registration/step1")}} className='custom-button mx-auto '>Get Started</button>

        
    </div>
   </div>
  )
}

export default Splash