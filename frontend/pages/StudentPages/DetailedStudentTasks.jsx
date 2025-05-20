import React from 'react'
import { useParams } from 'react-router'

const DetailedStudentTasks = () => {
    const {id} = useParams()
  return (
    <div>
        <h1>task id is : {id}</h1>
    </div>
  )
}

export default DetailedStudentTasks