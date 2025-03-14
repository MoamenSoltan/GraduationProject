import React, { useEffect } from 'react'
import { AiOutlineMenu } from 'react-icons/ai'
import { MdKeyboardArrowDown } from 'react-icons/md'
import { MdKeyboardArrowUp } from 'react-icons/md'

import { useStateContext } from '../contexts/ContextProvider'
import person from "../data/avatar.jpg"
import { useNavigate } from 'react-router'
import { trimText } from '../utils/trim'
//style={{ width:activeMenu?"":""}}
// ${activeMenu?"ml-[300px]":""} `


const NavBar = () => {
    const {activeMenu,setActiveMenu,auth}=useStateContext()
    const name=auth?.firstName?.concat(" ",auth.lastName) || "Guest"
    const navigate = useNavigate()
    useEffect(()=>{
        //TODO: API call here to fetch profile info , or fetch required data upon login
    },[])
  return (
    <div  className='flex  px-10 z-50 items-center justify-between shadow-sm  p-2 bg-[#FAFBFB]  '>
        <button onClick={()=>{setActiveMenu(prev=>!prev)}} className={`hover:bg-gray-200 p-2 text-xl rounded-full hover:cursor-pointer ${activeMenu?"md:ml-[300px] ml-[160px]":""} `}>
        <AiOutlineMenu/>
        </button>

        <button onClick={()=>{navigate("/studentDashboard/profile")}} className='hover:bg-gray-200 p-2 md:w-[250px] rounded-md flex flex-row justify-center items-center gap-2'>
            <div className='border-2 rounded-full border-[#0096C1] '>
            <img src={auth.personalImage} className='w-10 rounded-full ' alt="person" />
            </div>

{/* TODO: switch user with API data  */}
            <div className='sub-text flex flex-row items-center  '>
                <p>Hi, <span className='font-bold'>{trimText(name,10)}</span> </p>
                <MdKeyboardArrowDown className='w-5 h-5'/>

            </div>

        </button>
        {
            // TODO: here lies data to be fetched from API
            // profile && (
            //     <div className='absolute top-[80px] right-[40px] w-[250px] bg-[#fff] rounded-md shadow-md p-4 text-center'>
            //         <h2 className='sub-text'> Profile</h2>  
            //       <p className='sub-text'>Data to be fetched from API</p>
            //       <p className='sub-text'>Data to be fetched from API</p>

            //       <p className='sub-text'>Data to be fetched from API</p>

            //       <p className='sub-text'>Data to be fetched from API</p>

            //       <p className='sub-text'>Data to be fetched from API</p>
            //       <button onClick={()=>{setProfile(prev=>!prev)}} className='   mt-10 hover:scale-105 transition-all border-t-2 border-black'>
            //         <MdKeyboardArrowUp className='w-5 h-5'/>
            //       </button>


            //     </div>
            // )
        }


        
    </div>
  )
}

export default NavBar