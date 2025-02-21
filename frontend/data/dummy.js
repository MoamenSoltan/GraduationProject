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
      type: "Required",
      year: "FirstYear"
    },
    {
      id: 2,
      name: "Mathematics for Computing",
      code: "MATH104",
      instructor: "Prof. Alice Johnson",
      schedule: "Tue & Thu, 9:00 AM - 10:30 AM",
      credits: 4,
      studentsEnrolled: 130,
      type: "Required",
      year: "FirstYear"
    },
    {
      id: 3,
      name: "Introduction to AI",
      code: "AI100",
      instructor: "Dr. Alan Turing",
      schedule: "Fri, 2:00 PM - 4:00 PM",
      credits: 2,
      studentsEnrolled: 90,
      type: "Optional",
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
      studentsEnrolled: 110,
      type: "Required",
      year: "SecondYear"
    },
    {
      id: 5,
      name: "Operating Systems",
      code: "CS204",
      instructor: "Dr. Richard Stallman",
      schedule: "Mon & Wed, 11:00 AM - 12:30 PM",
      credits: 3,
      studentsEnrolled: 85,
      type: "Required",
      year: "SecondYear"
    },
    {
      id: 6,
      name: "Web Development Basics",
      code: "WD200",
      instructor: "Prof. Tim Berners-Lee",
      schedule: "Fri, 10:00 AM - 12:00 PM",
      credits: 2,
      studentsEnrolled: 70,
      type: "Recommended",
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
      type: "Required",
      year: "ThirdYear"
    },
    {
      id: 8,
      name: "Database Management Systems",
      code: "CS310",
      instructor: "Prof. Edgar Codd",
      schedule: "Mon & Wed, 3:00 PM - 4:30 PM",
      credits: 3,
      studentsEnrolled: 90,
      type: "Required",
      year: "ThirdYear"
    },
    {
      id: 9,
      name: "Cybersecurity Basics",
      code: "SEC301",
      instructor: "Dr. Kevin Mitnick",
      schedule: "Thu, 4:00 PM - 6:00 PM",
      credits: 2,
      studentsEnrolled: 50,
      type: "Recommended",
      year: "ThirdYear"
    },
    {
      id: 10,
      name: "Cloud Computing Fundamentals",
      code: "CC315",
      instructor: "Dr. Werner Vogels",
      schedule: "Wed, 10:00 AM - 12:00 PM",
      credits: 3,
      studentsEnrolled: 65,
      type: "Optional",
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
      type: "Required",
      year: "FourthYear"
    },
    {
      id: 12,
      name: "Software Engineering Principles",
      code: "SE401",
      instructor: "Prof. Martin Fowler",
      schedule: "Mon & Wed, 10:30 AM - 12:00 PM",
      credits: 3,
      studentsEnrolled: 80,
      type: "Required",
      year: "FourthYear"
    },
    {
      id: 13,
      name: "Big Data Analytics",
      code: "BD420",
      instructor: "Dr. Jeff Dean",
      schedule: "Fri, 3:00 PM - 5:00 PM",
      credits: 2,
      studentsEnrolled: 40,
      type: "Recommended",
      year: "FourthYear"
    },
    {
      id: 14,
      name: "Blockchain and Cryptography",
      code: "BC432",
      instructor: "Dr. Vitalik Buterin",
      schedule: "Thu, 2:00 PM - 4:00 PM",
      credits: 3,
      studentsEnrolled: 55,
      type: "Optional",
      year: "FourthYear"
    }
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
  
  
  
  