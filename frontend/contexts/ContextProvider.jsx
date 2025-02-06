
import React, { useContext, useState } from "react"
import { useLocation } from "react-router"
const stateContext=React.createContext()

export const ContextProvider = ({children})=>{
  const [user, setUser] = useState({
    email : "",
    password : "",
    firstName: "",
    lastName: "",
    dateOfBirth: "", // Ensure it's initially empty
    gender: "",
    phoneNumber: "+20",//check later
    address: "",
    city:"",
    country:"",
    highSchoolName: "",
    highSchoolGPA:"",
    highSchoolCertificate: null,
    facultyApplied: "FCI",
    preferredMajor: "",
    IDNumber: "",
    IDPhoto:null,
    personalPhoto :null,
    status: "", 
    
   

  })

  const [meter, setMeter] = useState(1)
  const incrementMeter= ()=>{
    setMeter((prev)=>prev+1)
  }
  const decrementMeter= ()=>{
    setMeter((prev)=>prev-1)
  }
 

  const Placeholder = "App_Name"
   return (
    <stateContext.Provider value={{
        Placeholder,user,setUser,
        meter,incrementMeter,setMeter,decrementMeter
    }}>
        {children}
    </stateContext.Provider>
   )

}

export const useStateContext = ()=>useContext(stateContext)

// 4 steps : 1- create a context 2- create a function to act as a provider , having children , in this function define any states or functions that will be used globally , and return children 3- make a custom hook that uses the useCOntext(stateContext) 4- wrap app in provider  5- destructure the useStateContext to get what you need in any component