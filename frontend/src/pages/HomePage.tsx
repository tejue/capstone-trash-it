import {useNavigate} from "react-router-dom";
import styled from "styled-components";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";

export default function HomePage() {

    const navigate = useNavigate();

    function handleClick() {
        navigate("/game")
    }


    return (
        <>
            <StyledSection>
                <GameBox>Start cleaning up and...</GameBox>
            </StyledSection>
            <ButtonBuzzer handleClick={handleClick} buttonText={"trash it"} $position={"right"}/>
        </>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const GameBox = styled.p`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  border: solid 1px #1f1e1e;
  border-radius: 5px;
  padding: 20px;
  background-color: #9d6101;
  width: 200px;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
`