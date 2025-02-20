import React from 'react'
import hero from "../data/hero3.png"

const Hero = () => {
  return (
    <div className=' h-[36vh] shadow-md  mt-10 p-10 flex justify-between flex-row items-center rounded-xl bg-main-bg'>
        <div className='flex flex-col w-full  justify-center'>
            <div>
                <p className='text-white text-lg font-semibold'>
                    {/* TODO:  Todays date from  API*/}
                    February 19 , 2025
                </p>
            </div>

{/* TODO: switch user with API data  */}

            <div className=' '>
               <h1 className='text-6xl font-semibold  text-white mt-[100px]'>Welcome Back , User!</h1>
               <h3 className='text-white mt-10'>Always stay updated in your student dashboard</h3>               

            </div>
        </div>

        <div className='w-[60%]'>
            <img src={hero} className='' alt="img" />
        </div>
    </div>
  )
}

export default Hero