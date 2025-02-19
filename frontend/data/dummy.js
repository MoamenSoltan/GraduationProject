import { TbReportAnalytics } from "react-icons/tb";
import { FaPen } from "react-icons/fa";
import { AiOutlineFileDone } from "react-icons/ai";
import { FaMoneyCheckDollar } from "react-icons/fa6";
import { IoIosCalendar } from "react-icons/io";
import { IoIosPerson } from "react-icons/io";
import { FaHandsHelping } from "react-icons/fa";
import { IoLogOutOutline } from "react-icons/io5";

// icons as just the name , when using them in map , wrap with </> to make it a component
export const links = [
    {
        name : "Analytics",
        icon : TbReportAnalytics 
 
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