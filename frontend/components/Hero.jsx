import React from 'react'
import hero from "../data/hero3.png"
import { useStateContext } from '../contexts/ContextProvider'

const Hero = () => {
    const {auth}=useStateContext()
    const date =new Date().toLocaleDateString()

    const formatter = new Intl.DateTimeFormat("en-US", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "2-digit"
    });

  return (
    <div className='h-[40vh]  shadow-md  mt-10 p-10 flex justify-between flex-row items-center rounded-xl bg-main-bg'>
        <div className='flex flex-col w-full  justify-center'>
            <div>
                <p className='text-white text-lg font-semibold'>
                    {formatter.format(new Date())}
                </p>
            </div>



            <div className=' '>
               <h1 className='md:text-6xl text-3xl font-semibold  text-white md:mt-[5%]'>Welcome Back, {auth.firstName}!</h1>
               <h3 className='text-white mt-10'>Always stay updated in your student dashboard</h3>               

            </div>
        </div>

        <div className='md:w-[60%] w-0'>
            <img src={hero} className='' alt="img" />
        </div>
    </div>
  )
}

export default Hero