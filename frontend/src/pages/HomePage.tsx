import {useNavigate} from "react-router-dom";
import styled from "styled-components";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";

export default function HomePage() {

    const navigate = useNavigate();

    function handleClick() {
        navigate("/game")
    }

    return (
        <>
            <Background $backgroundColor={"none"}/>
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
  padding: 20px;
  background-color: #E6F0E9;
  width: 300px;
  height: 300px;
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`
