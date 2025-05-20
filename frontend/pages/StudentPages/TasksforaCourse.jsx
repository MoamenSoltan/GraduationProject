import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const TasksforaCourse = () => {
  const [tasks, setTasks] = useState([]);
  const [selectedDeadline, setSelectedDeadline] = useState("");
  const { courseId } = useParams(); 
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate()
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await axiosPrivate.get(`/task/student/course/${courseId}${selectedDeadline}`);
        setTasks(response.data);
        console.log("tasks fetched", response.data);
      } catch (error) {
        toast.error(`An error occurred while fetching tasks: ${error.response.data.detail}`);
      }
    };

    fetchTasks();
  }, [courseId, axiosPrivate,selectedDeadline]);

  const openAttachment = (url) => {
    window.open(url, '_blank');
  };

  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
      <div className='w-full flex justify-between items-center mb-6'>
        <h2 className="text-2xl font-semibold text-gray-800">Tasks for this Course</h2>
        <select
          name="deadline"
          value={selectedDeadline}
          onChange={(e) => setSelectedDeadline(e.target.value)}
          className='border-2 rounded-md outline-none p-2'
        >
          <option value="">All</option>
          <option value="/upcoming-deadline">Upcoming deadline</option>
          <option value="/past-deadline">Past deadline</option>
          <option value="/complete">Submitted</option>
        </select>
      </div>

      <div className="flex flex-wrap gap-6">
        {tasks.length > 0 ? (
          tasks.map((task) => (
            <div onClick={()=>navigate(`/studentDashboard/Tasks/${task.id}`)}
              key={task.id}
              className="w-80 bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow p-6"
            >
              <h3 className="text-xl font-bold text-gray-800 mb-2">{task.taskName}</h3>
              <p className="text-gray-600 mb-2">{task.description}</p>
              <p className="text-gray-500 text-sm mb-2">Max Grade: {task.maxGrade}</p>
              <p className="text-gray-500 text-sm mb-2">Deadline: {task.deadline}</p>
              <p className="text-gray-400 text-xs mb-4">Created at: {new Date(task.createdAt).toLocaleDateString()}</p>

              {task.attachment && (
                <button
                  onClick={() => openAttachment(task.attachment)}
                  className="text-blue-500 hover:underline text-sm"
                >
                  View Attachment
                </button>
              )}
            </div>
          ))
        ) : (
          <div className="text-gray-400 text-lg">No tasks found for this course.</div>
        )}
      </div>
    </div>
  );
};

export default TasksforaCourse;
