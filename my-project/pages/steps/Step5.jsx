import React from 'react'
import ProgressBar from '../../components/ProgressBar'
import { motion } from 'motion/react'

const Step5 = () => {
  return (
    <motion.div initial={{ x:"-5%", }}
    animate={{ x:0,  }}
    exit={{ x:"-5%",  }}
    transition={{ duration: 0.5 }}>
       <div className='w-full mt-10 flex justify-center'>
       <ProgressBar />
       </div>
    </motion.div>
  )
}

export default Step5