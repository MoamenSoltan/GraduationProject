import React from 'react'
import ProgressBar from '../../components/ProgressBar'
import { motion } from 'motion/react'
import Check from '../../data/check.svg'
import { useStateContext } from '../../contexts/ContextProvider'
import { useNavigate } from 'react-router'
/**
 * 
 * notes : for in 
 * bracket notation 
 * //bracket notation for loops , if we use user.key itll look for a value of key which doesnt exist (user.key is used for accessing known properties , bracket notation when the property is stored in a variable (in a loop))
 */

const Step5 = () => {
    const { user, setUser } = useStateContext();
    const navigate = useNavigate()

    //async
  const handleSubmit = async () => {
    const formData = new FormData()
    for(let key in user){//for in 
      formData.append(key,user[key])//bracket notation
     
      //expalanation below

      //TODO: API call here 



      navigate("/registration/done")
    
    }
    // navigate(`/dashboard/${user.id}`)
    for (let pair of formData.entries()) { // to log form data
      console.log(pair[0], pair[1]);
    }
    

    
    
  }

  return (
    <motion.div initial={{ x:"-5%", }}
    animate={{ x:0,  }}
    exit={{ x:"-5%",  }}
    transition={{ duration: 0.5 }}>
       <div className='w-full mt-10 flex justify-center'>
       <ProgressBar />
       </div>

       <div className='md:w-[55%] text-center md:p-0 p-4 mt-[7%] m-auto flex-col flex items-center justify-center'>
      <img src={Check} className='w-[100px] h-[100px]' alt="check" />
       <h1 className='text-center main-text'>You are almost done!</h1>
       <p className='sub-text'>by pressing the button below , you agree to our terms and conditions</p>

       <button onClick={handleSubmit} className='custom-button'>Finish</button>
        
       </div>
    </motion.div>

    
  )
}

export default Step5

//console.log(user[key]);//bracket notation for loops , if we use user.key itll look for a value of key which doesnt exist (user.key is used for accessing known properties , bracket notation when the property is stored in a variable (in a loop))