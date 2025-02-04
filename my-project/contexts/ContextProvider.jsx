
import React, { useContext, useState } from "react"
const stateContext=React.createContext()

export const ContextProvider = ({children})=>{
  const [user, setUser] = useState({
    email : "",
    password : "",
    name:"moamen"

  })
 

  const Placeholder = "App_Name"
   return (
    <stateContext.Provider value={{
        Placeholder,user,setUser
    }}>
        {children}
    </stateContext.Provider>
   )

}

export const useStateContext = ()=>useContext(stateContext)

// 4 steps : 1- create a context 2- create a function to act as a provider , having children , in this function define any states or functions that will be used globally , and return children 3- make a custom hook that uses the useCOntext(stateContext) 4- wrap app in provider  5- destructure the useStateContext to get what you need in any component