import React from "react";
import { NavLink, Outlet, Route, Routes, useNavigate } from "react-router";
import ImageToText from "./Tools/ImageToText";
import TextSummarization from "./Tools/TextSummarization";

const Tools = () => {
  const activeLink =
    "flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-white text-md m-2 p-4 ";
  const normalLink =
    "flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-gray-600  dark:hover:text-black hover:bg-light-gray text-md m-2 p-4 ";

  const navigate = useNavigate();
  return (
    <div className="mt-10 w-[80%] m-auto">

      <div className="flex flex-row w-fit m-auto ">
      {["Text-summarization", "Image-to-text","Summarize-video"].map((link) => (
        <NavLink
          to={`/studentDashboard/Tools/${link}`}
          key={link}
          style={({ isActive }) => ({
            backgroundColor: isActive ? "#0096C1" : "",
          })}
          className={({ isActive }) => (isActive ? activeLink : normalLink)}
        >
          <button>{link.replaceAll('-'," ")}</button>
        </NavLink>
      ))}
      </div>

      <Outlet />
    </div>
  );
};

export default Tools;
