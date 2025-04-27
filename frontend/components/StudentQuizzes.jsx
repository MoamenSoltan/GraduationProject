import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router'
import { trimText } from '../utils/trim'
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import toast from 'react-hot-toast'

const StudentQuizzes = () => {
    const [allQuizzes, setallQuizzes] = useState([])
        const [courses, setcourses] = useState([])
    
    const axiosPrivate=useAxiosPrivate()

    useEffect(() => {
        // const fetchQuizzes = async () => {
        //   try {
        //     const response = await axiosPrivate.get("/student/quiz");
        //     setallQuizzes(response.data);
        //   } catch (error) {
        //     toast.error("Error fetching quizzes");
        //   }
        // };
    
       
    
        // fetchQuizzes();

        const fetchCourses = async ()=>{
          try {
              const response = await axiosPrivate.get("/student/courses/signed")
              setcourses(response.data)
              console.log("courses fetched",response.data);
              
          } catch (error) {
              toast.error(`an error occurred while fetching courses ${error}`)
          }
      }

      fetchCourses()
        
      }, []);

    const navigate=useNavigate()
  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
    <div className='w-full flex justify-between'>
    <h2 className="text-2xl font-semibold mb-4 text-gray-800">Select a course</h2>

    
  
    </div>
    <div className="flex w-full flex-row flex-wrap gap-4">
  {courses?.length>0
  ?courses.map((course) => (
    <div
      onClick={() =>
        navigate(
          `/studentDashboard/Quizzes/all/${course.courseId}`
        )
      }
      key={course.courseId}
      className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 transform cursor-pointer"
    >
      <div className="p-4 space-y-2">
        <h3 className="text-xl font-semibold text-gray-800">
          {course.courseName}
        </h3>
        <p className="text-sm text-gray-600">Code: {course.courseCode}</p>
        <p className="text-sm text-gray-600 capitalize">
          Grade: {course?.grade?.replaceAll("_", " ").toLowerCase()}
        </p>
        <p className="text-sm text-gray-600">
          Semester: {course.semester.semesterName}
        </p>
        <p className="text-sm text-gray-600">
          Year: {course.semester.year}
        </p>
        <div className="flex justify-between items-center mt-2">
          <button className="text-blue-500 hover:underline">
            View Details
          </button>
        </div>
      </div>
 
    </div>
    
  ))
:
<div>
      <h2 className="text-xl  mb-4 text-gray-400">
        No Courses Found
      </h2>
 

    </div>
}


</div>
    
</div>
  )
}

export default StudentQuizzes

/**
 *  
 */

    /**
     *  <div className='md:w-[80%] w-full m-auto mt-10'>
        <h2 className="text-2xl font-semibold mb-4 text-gray-800">Quizzes</h2>
        {
                allQuizzes.length>0 
                ?allQuizzes.map((quiz) => (
                  <div 
                    onClick={() => navigate(`/studentDashboard/Quizzes/${quiz.ID}`)} 
                    key={quiz.ID} 
                    className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 transform cursor-pointer"
                  >
                    <div className="relative overflow-hidden rounded-t-lg">
                      
                    </div>
                    <div className="p-4 space-y-2">
                      <h3 className="text-xl font-semibold text-gray-800">{quiz.name}</h3>
                      <p className="text-sm text-gray-600">Description : {trimText(quiz.description,20)}</p>
                      <p className="text-sm text-gray-600">number of questions : {quiz.numberOfQuestions}</p>
                      <div className="flex justify-between items-center mt-2">
                        <button className="text-blue-500 hover:underline">View Details</button>
                      </div>
                    </div>
                 
             
                  </div>
                  
                ))
                : <div>
                  <h2 className="text-xl  mb-4 text-gray-400">
                    No Quizzes Found
                  </h2>
               
        
                </div>
              }
    </div>
     */