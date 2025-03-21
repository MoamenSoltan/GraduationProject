import React from 'react'
import RequestsComponent from "../../components/ReguestsComponent"
const SubmissionRequests = () => {
  return (
    <div className='w-[80%] m-auto mt-10'>
      <h1 className='sub-text-2'>Submission Requests</h1>
      <div className='flex flex-col items-center justify-center m-auto'>
        <RequestsComponent/>
      </div>
    </div>
  )
}

export default SubmissionRequests