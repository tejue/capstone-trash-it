import {useNavigate} from "react-router-dom";

export default function HomePage() {

    const navigate = useNavigate();

    function handleClick() {
        navigate("/game")
    }

    return (
        <>
            <p>Start cleaning up and...</p>
            <button onClick={handleClick}>TRASH IT</button>
        </>
    )
}