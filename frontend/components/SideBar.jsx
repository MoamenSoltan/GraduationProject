import React from 'react'
import { useStateContext } from '../contexts/ContextProvider'
import { Link, NavLink } from 'react-router-dom'
import { links } from '../data/dummy'
const SideBar = () => {
  const {activeMenu,setActiveMenu}= useStateContext()

  const activeLink='flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-white text-md m-2 '
  const normalLink='flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-gray-600  dark:hover:text-black hover:bg-light-gray text-md m-2 '

  return (
    <div className="  overflow-auto md:overflow-hidden  h-screen md:hover:overflow-auto pb-10 shadow-md ">
     
      {activeMenu && (
        <>
        

          <div className="mt-[100px] flex flex-col gap-3 pr-2" >
          {
                    links.map((link)=>(
                      <NavLink to={`/studentDashboard/${link.name}` } key={link.name} style={({isActive})=>({backgroundColor:
                      isActive?"#0096C1":''})} className={({isActive})=>isActive?activeLink:normalLink }>
                            <div className='text-lg '>{<link.icon/>}</div>
                            <span className="capitalize text-lg">{link.name}</span>
                      </NavLink>
                    ))
                  }
          </div>

        </>
      )}
    </div>
  )
}

export default SideBar