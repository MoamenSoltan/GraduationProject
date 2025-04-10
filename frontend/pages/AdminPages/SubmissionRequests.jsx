import React from 'react'
import RequestsComponent from "../../components/ReguestsComponent"
const SubmissionRequests = () => {
  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
      
      <div className='flex flex-col items-center justify-center m-auto'>
        <RequestsComponent/>
      </div>
    </div>
  )
}

export default SubmissionRequests