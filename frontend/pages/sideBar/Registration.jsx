import React, { useEffect, useState } from 'react';

import { LuBookX } from "react-icons/lu";
import CapacityMeter from '../../components/CapacityMeter';
import { div } from 'motion/react-client';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const Registration = () => {
  


  const [fetchedCourses, setfetchedCourses] = useState([])
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [loading, setloading] = useState(false)
  const axiosPrivate = useAxiosPrivate()
  // use a state to control the input filed -- checkbox
  // very important
  /**
   * very important in dealing with tables , 
   * in case of overflow , wrap the table in another div and add the overflow-x-auto to it 
   */

  useEffect(()=>{
    const fetchCourses = async ()=>{
      try {
        const response = await axiosPrivate.get("/student/courses")
        setfetchedCourses(response.data)
        console.log("fetched COurses :",response.data);
        
      } catch (error) {
        toast.error(`an error occurred : ${error} `)
      }
    }
    fetchCourses()
  },[])

  // Handle checkbox toggle
  const handleCheckboxChange = (courseId) => {
    setSelectedCourses((prev) =>
      prev.includes(courseId)
        ? prev.filter((id) => id !== courseId) // Remove if unchecked
        : [...prev, courseId] // Add if checked
    );
  };

  // Handle course registration (API call placeholder)
  const handleRegisterCourses = async () => {
    if (loading)
      return
    setloading(true)
    const registeredCourses = fetchedCourses.filter((course) =>
      selectedCourses.includes(course.courseId)
    );
    const formattedCourses = registeredCourses.map(course => ({
      courseId: course.courseId,
      // yearLevel: course.semester.yearLevel,
      // semesterName: course.semester.semesterName
    }));
    
    console.log(formattedCourses);

    console.log("Registered Courses:", formattedCourses);
  
    
    try {
      const response = await axiosPrivate.post("/student/course",registeredCourses)
      toast.success(`Courses registered successfully!`)
    } catch (error) {
      toast.error(`an error occurred : ${error}`)
    } finally {
      setloading(false)
    }
  };

  return (
    
      
      <div className="flex flex-col space-y-10 h-[70vh] justify-center items-center md:w-[60%] w-[90%] mx-auto overflow-x-scroll ">
        {fetchedCourses.length > 0 ? (
         <div className='w-full overflow-x-auto'>
           <table className="table-auto w-full border-collapse border border-gray-300 shadow-lg rounded-lg ">
            {/* Table Head */}
            <thead className="bg-blue-500 text-white text-lg uppercase">
              <tr>
                <th className="px-4 py-3 ">Register</th>
                <th className="px-4 py-3">Code</th>
                <th className="px-4 py-3">Course Name</th>
                <th className="px-4 py-3">Instructor</th>
                <th className="px-4 py-3">Course Level</th>
                <th className="px-4 py-3">Credits</th>
                <th className="px-4 py-3 text-left">Capacity</th>
              </tr>
            </thead>

            {/* Table Body */}
            <tbody className="divide-y-4 divide-gray-200">
              {fetchedCourses.map((course) => (
                <tr key={course.courseId} className="text-center bg-white hover:bg-gray-100 transition">
                  <td className="px-4 py-3">
                    <label className="flex justify-center">
                      <input
                        type="checkbox"
                        className="peer hidden"
                        checked={selectedCourses.includes(course.courseId)}
                        onChange={() => handleCheckboxChange(course.courseId)}
                      />
                      <div className="w-5 h-5 border-2 border-gray-400 rounded-md 
                        peer-checked:bg-[#0096C1] peer-checked:border-blue-600 
                        peer-checked:ring-2 peer-checked:ring-blue-300 
                        transition cursor-pointer"></div>
                    </label>
                  </td>
                  <td className="px-4 py-3">{course.courseCode}</td>
                  <td className="px-4 py-3">{course.courseName}</td>
                  <td className="px-4 py-3">{course?.instructor?.firstName + " " + course?.instructor?.lastName}</td>
                  <td className="px-4 py-3">{course.year?.replace("_"," ").toLowerCase()}</td>
                  <td className="px-4 py-3">{course.credit}</td>
                  <td className="px-4 py-3">
                    <CapacityMeter enrolled={course.studentEnrolled} max={course.maxStudents} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
         </div>
        ) : (
          <div className="text-center text-gray-600 text-xl font-semibold flex flex-col items-center">
            <LuBookX className="text-6xl text-blue-500 mb-3" /> {/* Icon */}
            No courses available for registration.
          </div>
        )}

           {/* Register Button */}
      <button
        onClick={handleRegisterCourses}
        className={`mt-4 px-6 py-2 bg-[#0096C1] text-white rounded-lg shadow-md 
          hover:bg-blue-600 transition disabled:opacity-50 ${fetchedCourses.length===0&& "hidden"}`}
        disabled={selectedCourses.length === 0}
      >
        Register Selected Courses
      </button>
      </div>

   
   
  );
};

export default Registration;


// import React, { useState } from 'react';
// import { coursesData } from '../../data/dummy';
// import { LuBookX } from "react-icons/lu";
// import CapacityMeter from '../../components/CapacityMeter';
// import { div } from 'motion/react-client';

// const Registration = () => {
//   const [selectedCourses, setSelectedCourses] = useState([]);
//   // use a state to control the input filed -- checkbox
//   // very important
//   /**
//    * very important in dealing with tables , 
//    * in case of overflow , wrap the table in another div and add the overflow-x-auto to it 
//    */

//   // Handle checkbox toggle
//   const handleCheckboxChange = (courseId) => {
//     setSelectedCourses((prev) =>
//       prev.includes(courseId)
//         ? prev.filter((id) => id !== courseId) // Remove if unchecked
//         : [...prev, courseId] // Add if checked
//     );
//   };

//   // Handle course registration (API call placeholder)
//   const handleRegisterCourses = () => {
//     const registeredCourses = coursesData.filter((course) =>
//       selectedCourses.includes(course.id)
//     );

//     console.log("Registered Courses:", registeredCourses);
//     window.alert("Registered Courses:\n"+ registeredCourses.map((course)=>(course.name)).join("\n"))
//     // + not ',' in alert because alert only accepts one argument 
    
//     // TODO: API Call here
//     // fetch("API_ENDPOINT", {
//     //   method: "POST",
//     //   headers: { "Content-Type": "application/json" },
//     //   body: JSON.stringify({ courses: registeredCourses }),
//     // })
//     // .then(response => response.json())
//     // .then(data => console.log("API Response:", data))
//     // .catch(error => console.error("Error:", error));
//   };

//   return (
    
      
//       <div className="flex flex-col space-y-10 h-[70vh] justify-center items-center md:w-[60%] w-[90%] mx-auto overflow-x-scroll ">
//         {coursesData.length > 0 ? (
//          <div className='w-full overflow-x-auto'>
//            <table className="table-auto w-full border-collapse border border-gray-300 shadow-lg rounded-lg ">
//             {/* Table Head */}
//             <thead className="bg-blue-500 text-white text-lg uppercase">
//               <tr>
//                 <th className="px-4 py-3 ">Register</th>
//                 <th className="px-4 py-3">Code</th>
//                 <th className="px-4 py-3">Course Name</th>
//                 <th className="px-4 py-3">Instructor</th>
//                 <th className="px-4 py-3">Course Level</th>
//                 <th className="px-4 py-3">Credits</th>
//                 <th className="px-4 py-3 text-left">Capacity</th>
//               </tr>
//             </thead>

//             {/* Table Body */}
//             <tbody className="divide-y-4 divide-gray-200">
//               {coursesData.map((course) => (
//                 <tr key={course.id} className="text-center bg-white hover:bg-gray-100 transition">
//                   <td className="px-4 py-3">
//                     <label className="flex justify-center">
//                       <input
//                         type="checkbox"
//                         className="peer hidden"
//                         checked={selectedCourses.includes(course.id)}
//                         onChange={() => handleCheckboxChange(course.id)}
//                       />
//                       <div className="w-5 h-5 border-2 border-gray-400 rounded-md 
//                         peer-checked:bg-[#0096C1] peer-checked:border-blue-600 
//                         peer-checked:ring-2 peer-checked:ring-blue-300 
//                         transition cursor-pointer"></div>
//                     </label>
//                   </td>
//                   <td className="px-4 py-3">{course.code}</td>
//                   <td className="px-4 py-3">{course.name}</td>
//                   <td className="px-4 py-3">{course.instructor}</td>
//                   <td className="px-4 py-3">{course.year}</td>
//                   <td className="px-4 py-3">{course.credits}</td>
//                   <td className="px-4 py-3">
//                     <CapacityMeter enrolled={course.studentsEnrolled} max={course.maxStudents} />
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//          </div>
//         ) : (
//           <div className="text-center text-gray-600 text-xl font-semibold flex flex-col items-center">
//             <LuBookX className="text-6xl text-blue-500 mb-3" /> {/* Icon */}
//             No courses available for registration.
//           </div>
//         )}

//            {/* Register Button */}
//       <button
//         onClick={handleRegisterCourses}
//         className="mt-4 px-6 py-2 bg-[#0096C1] text-white rounded-lg shadow-md 
//           hover:bg-blue-600 transition disabled:opacity-50"
//         disabled={selectedCourses.length === 0}
//       >
//         Register Selected Courses
//       </button>
//       </div>

   
   
//   );
// };

// export default Registration;
