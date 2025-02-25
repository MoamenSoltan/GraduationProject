import { TbReportAnalytics } from "react-icons/tb";
import { FaPen } from "react-icons/fa";
import { AiOutlineFileDone } from "react-icons/ai";
import { FaMoneyCheckDollar } from "react-icons/fa6";
import { IoIosCalendar } from "react-icons/io";
import { IoIosPerson } from "react-icons/io";
import { FaHandsHelping } from "react-icons/fa";
import { IoLogOutOutline } from "react-icons/io5";
import { BsMegaphone } from "react-icons/bs";


// icons as just the name , when using them in map , wrap with </> to make it a component
export const links = [
    {
        name : "Analytics",
        icon : TbReportAnalytics 
 
    },
    {
        name : "Announcements",
        icon : BsMegaphone
    }
    ,
    {
        name : "Registration",
        icon : FaPen 

    },
    {
        name : "Courses",
        icon : AiOutlineFileDone 

    },
    {
        name : "Payment",
        icon : FaMoneyCheckDollar 
    },
    {
        name : "Events",
        icon : IoIosCalendar 
    },
    {
        name : "Instructors",
        icon : IoIosPerson 

    },
    {
        name : "Help",
        icon : FaHandsHelping 

    },
    {
        name : "Logout",
        icon : IoLogOutOutline 

    }
]

export const announcementsData = [
    {
      id: 1,
      title: "Assignment 3 Deadline Extended",
      description: "The deadline for Assignment 3 has been extended to March 10. Make sure to submit it before the new deadline.",
      date: "2025-02-20",
      time: "10:00 AM",
      instructor: "Dr. Ahmed Hassan",
      course: "AI Fundamentals",
      type: "Assignment"
    },
    {
      id: 2,
      title: "Guest Lecture on Machine Learning",
      description: "Join us for a special lecture on Machine Learning trends by Dr. Sarah Khalid on February 25.",
      date: "2025-02-25",
      time: "2:00 PM",
      instructor: "Prof. Omar Saleh",
      course: "Machine Learning",
      type: "Event"
    },
    {
      id: 3,
      title: "Midterm Exam ScheduleScheduleScheduleScheduleScheduleScheduleSchedule",
      description: "The midterm exams will start from March 5. Check the student portal for your detailed exam schedule.",
      date: "2025-03-05",
      time: "8:00 AM",
      instructor: "Department of CS",
      course: "All Courses",
      type: "Exam"
    }
  ];
  
  export const coursesData = [
    // First Year Courses
    {
      id: 1,
      name: "Introduction to Computer Science",
      code: "CS101",
      instructor: "Dr. John Doe",
      schedule: "Mon & Wed, 10:00 AM - 11:30 AM",
      credits: 3,
      studentsEnrolled: 150,
      maxStudents : 200,
      type: "Required",
      year: "FirstYear"
    },
    
  
    // Second Year Courses
    {
      id: 4,
      name: "Data Structures & Algorithms",
      code: "CS202",
      instructor: "Prof. Jane Smith",
      schedule: "Tue & Thu, 2:00 PM - 3:30 PM",
      credits: 4,
      studentsEnrolled: 160,
      maxStudents : 300,
      type: "Required",
      year: "SecondYear"
    },
    
    // Third Year Courses
    {
      id: 7,
      name: "Machine Learning Fundamentals",
      code: "AI305",
      instructor: "Dr. Alan Turing",
      schedule: "Fri, 1:00 PM - 3:00 PM",
      credits: 3,
      studentsEnrolled: 75,
      maxStudents : 80,

      type: "Required",
      year: "ThirdYear"
    },
   
  
    // Fourth Year Courses
    {
      id: 11,
      name: "Advanced Artificial Intelligence",
      code: "AI450",
      instructor: "Dr. Andrew Ng",
      schedule: "Tue & Thu, 1:00 PM - 2:30 PM",
      credits: 4,
      studentsEnrolled: 60,
      maxStudents : 200,
      type: "Required",
      year: "FourthYear"
    },
    
  ];
  
  

  export const instructorsData = [
    {
      id: 1,
      image: "https://randomuser.me/api/portraits/men/32.jpg",
    },
    {
      id: 2,
      image: "https://randomuser.me/api/portraits/women/45.jpg",
    },
    {
      id: 3,
      image: "https://randomuser.me/api/portraits/men/54.jpg",
    },
    {
      id: 4,
      image: "https://randomuser.me/api/portraits/women/60.jpg",
    },
    {
      id: 5,
      image: "https://randomuser.me/api/portraits/men/12.jpg",
    },
  ];
  
  
  
  