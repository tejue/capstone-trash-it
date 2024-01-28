import {useNavigate} from "react-router-dom";
import styled from "styled-components";

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
            <ButtonContainer>
                <BuzzerButton onClick={handleClick}><StyledSpan>trash it</StyledSpan></BuzzerButton>
            </ButtonContainer>
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

const ButtonContainer = styled.div`
  display: flex;
  //justify-content: flex-end;
  margin: 40px 30px;
  position: fixed;
  bottom: 0;
  right: 0;
`
const BuzzerButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid #1f1e1e;
  background: linear-gradient(0deg, #013b01, #026002);
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  border: 1px solid #015701;
  border-radius: 50%;
  background: linear-gradient(180deg, #013b01, #026002);
  font-size: 16px;
  color: #c0bdbd;
`
