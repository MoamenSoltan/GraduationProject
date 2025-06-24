import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";
import Modal from "../../components/Modal";
import dayjs from "dayjs";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

const Semesters = () => {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [semesters, setSemesters] = useState([]);
  const [modal, setModal] = useState(false);

  const [semesterForm, setSemesterForm] = useState({
    yearLevel: "",
    semesterName: "",
    startDate: null,
    endDate: null,
    isActive: false,
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    const fetchSemesters = async () => {
      try {
        const response = await axiosPrivate.get(`/admin/semester`);
        setSemesters(response.data);
        console.log("date:", response.data);
      } catch (error) {
        toast.error(`An error occurred: ${error}`);
      }
    };

    fetchSemesters();
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setSemesterForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleDateChange = (name, value) => {
    setSemesterForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validate = () => {
    const newErrors = {};
    if (!semesterForm.yearLevel) newErrors.yearLevel = "Year Level is required";
    if (!semesterForm.semesterName)
      newErrors.semesterName = "Semester Name is required";
    if (!semesterForm.startDate) newErrors.startDate = "Start Date is required";
    if (!semesterForm.endDate) newErrors.endDate = "End Date is required";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    const payload = {
      ...semesterForm,
      startDate: semesterForm.startDate.toISOString(),
      endDate: semesterForm.endDate.toISOString(),
    };

    try {
      await axiosPrivate.post("/admin/semester", payload);
      toast.success("Semester added successfully!");
      setModal(false);
      setSemesterForm({
        yearLevel: 0,
        semesterName: "",
        startDate: dayjs(),
        endDate: dayjs(),
        isActive: false,
      });
      setErrors({});
      // re-fetch semesters
      const response = await axiosPrivate.get(`/admin/semester`);
      setSemesters(response.data);
    } catch (error) {
      toast.error(
        `Error adding semester: ${
          error.response?.data?.detail || "Unknown error"
        }`
      );
    }
  };

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Semesters</h2>

      {/* Table */}
      <div className="overflow-x-auto">
        <table className="w-full border border-gray-300 text-left text-sm">
          <thead className="bg-gray-100">
            <tr>
              <th className="py-2 px-4 border-b">Year</th>
              <th className="py-2 px-4 border-b">Semester</th>
              <th className="py-2 px-4 border-b">Start Date</th>
              <th className="py-2 px-4 border-b">End Date</th>
              <th className="py-2 px-4 border-b">Active</th>
            </tr>
          </thead>
          <tbody>
            {semesters.map((sem, index) => (
              <tr key={index} className="hover:bg-gray-50">
                <td className="py-2 px-4 border-b">{sem.yearLevel}</td>
                <td className="py-2 px-4 border-b">{sem.semesterName}</td>
                <td className="py-2 px-4 border-b">
                  {new Date(sem.startDate).toLocaleDateString()}
                </td>
                <td className="py-2 px-4 border-b">
                  {new Date(sem.endDate).toLocaleDateString()}
                </td>
                <td className="py-2 px-4 border-b">
                  {sem.isActive ? "Yes" : "No"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Add Button */}
      <button
        onClick={() => setModal(true)}
        className="block mx-auto mt-8 p-4 w-20 h-20 rounded-full bg-blue-600 text-white text-3xl shadow-lg hover:scale-110 transition-all"
        title="Add Material"
      >
        +
      </button>

      {/* Modal with Form */}
      <Modal open={modal} onClose={() => setModal(false)}>
        <form onSubmit={handleSubmit} className="p-4 space-y-4">
          <h2 className="text-xl font-bold mb-4">Add Semester</h2>

          {/* Year Level Select */}
          <select
            name="yearLevel"
            value={semesterForm.yearLevel}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          >
            <option value="">Select Year</option>
            {[...Array(6)].map((_, index) => {
              const year = new Date().getFullYear() + index;
              return (
                <option key={year} value={year}>
                  {year}
                </option>
              );
            })}
          </select>
          {errors.yearLevel && (
            <p className="text-red-500">{errors.yearLevel}</p>
          )}

          {/* Semester Name Select */}
          <select
            name="semesterName"
            value={semesterForm.semesterName}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          >
            <option value="">Select Semester</option>
            <option value="Spring">Spring</option>
            <option value="Summer">Summer</option>
            <option value="Fall">Fall</option>
          </select>
          {errors.semesterName && (
            <p className="text-red-500">{errors.semesterName}</p>
          )}

          <div className="flex gap-2">
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                label="Start Date"
                value={semesterForm.startDate}
                onChange={(val) => handleDateChange("startDate", val)}
                className="w-full"
              />
            </LocalizationProvider>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                label="End Date"
                value={semesterForm.endDate}
                onChange={(val) => handleDateChange("endDate", val)}
                className="w-full"
              />
            </LocalizationProvider>
          </div>
          {(errors.startDate || errors.endDate) && (
            <p className="text-red-500">{errors.startDate || errors.endDate}</p>
          )}

          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              name="isActive"
              checked={semesterForm.isActive}
              onChange={handleChange}
            />
            Active Semester
          </label>

          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
          >
            Submit
          </button>
        </form>
      </Modal>
    </div>
  );
};

export default Semesters;
